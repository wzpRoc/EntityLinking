package org.ailab.tool.wikiProcess.p0_file;

import org.ailab.wimfra.util.FileUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: lutingming
 * Date: 16-1-4
 * Time: 下午1:41
 * Desc: 维基百科文章XML分割器
 * 1. 每次读取一个page
 * 2. 如果ns!=0或重定向，那么略过
 * 3. 按照title第一个字符分别存储
 */
public class WikiPageArticleXMLSplitter {
    static final String srcFilePath = "G:\\Wikipedia\\enwiki-20151102-pages-articles-multistream.xml";
//    static final String srcFilePath = "G:\\Wikipedia\\enwiki-20151102-pages-articles-multistream-sample.xml";
    static final String desDirPath = "F:\\Wikipedia\\pages-articles\\";

    static Logger logger = Logger.getLogger(WikiPageArticleXMLSplitter.class);
    static BufferedReader reader;
    static Map<String, BufferedWriter> writerMap;


    static {
        writerMap = new HashMap<String, BufferedWriter>();
        try {
            for (String fileTag : WikiPageArticleXMLUtil.fileTagList) {
                BufferedWriter bufferedWriter = createBufferedWriter(fileTag);
                writerMap.put(fileTag, bufferedWriter);
                writeFileHead(fileTag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedWriter createBufferedWriter(String fileTag) throws IOException {
        String desFilePath = desDirPath + fileTag + ".xml";
        return new BufferedWriter(new FileWriter(new File(desFilePath)));
    }

    public static void main(String[] args) {
        File srcFile = new File(srcFilePath);
        try {
            reader = new BufferedReader(new FileReader(srcFile));
            FileUtil.mkdirIfNotExist(desDirPath);

            int writtenPageCount=0;
            int ignoredPageCount=0;
            while (true) {
                WikiPageArticleXML wikiPageArticleXML = readPage();
                if (wikiPageArticleXML == null) {
                    System.out.println("written="+writtenPageCount+", ignored="+ignoredPageCount);
                    break;
                } else {
                    if(needToBeWrite(wikiPageArticleXML)) {
                        writePage(wikiPageArticleXML);
                        writtenPageCount++;
                        if(writtenPageCount%10000==0) {
                            System.out.println("written="+writtenPageCount+", ignored="+ignoredPageCount+", ["+wikiPageArticleXML.title+"]");
                        }
                    } else {
                        ignoredPageCount++;
                    }
                }
            }

            for (String fileTag : WikiPageArticleXMLUtil.fileTagList) {
                writeFileTail(fileTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeReader();
            closeWriters();
        }
    }


    private static WikiPageArticleXML readPage() throws IOException {
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                return null;
            }

            if (line.trim().equals("<page>")) {
                // create an object
                WikiPageArticleXML wikiPageArticleXML = new WikiPageArticleXML();
                wikiPageArticleXML.addLine(line);
                // read until end of this page
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        logger.warn("Unexpected end of file before </page> occurs.");
                        return wikiPageArticleXML;
                    }

                    wikiPageArticleXML.addLine(line);

                    String lineTrimmed = line.trim();
                    if (lineTrimmed.equals("</page>")) {
                        return wikiPageArticleXML;
                    } else {
                        if (wikiPageArticleXML.title == null && lineTrimmed.startsWith("<title>") && lineTrimmed.endsWith("</title>")) {
                            wikiPageArticleXML.title = lineTrimmed.substring(7, lineTrimmed.length() - 8);
                        } else if (wikiPageArticleXML.namespace == -1 && lineTrimmed.startsWith("<ns>") && lineTrimmed.endsWith("</ns>")) {
                            wikiPageArticleXML.namespace = Integer.parseInt(lineTrimmed.substring(4, lineTrimmed.length() - 5));
                        } else if (!wikiPageArticleXML.isRedirect && lineTrimmed.startsWith("<redirect title=\"")) {
                            wikiPageArticleXML.isRedirect = true;
                        } else {
                            // I don't care.
                        }
                    }
                }
            }
        }
    }

    private static boolean needToBeWrite(WikiPageArticleXML wikiPageArticleXML) throws IOException {
        return wikiPageArticleXML.namespace==0
                && !wikiPageArticleXML.isRedirect;
    }


    private static void writePage(WikiPageArticleXML wikiPageArticleXML) throws IOException {
        String fileTag = WikiPageArticleXMLUtil.getFileTag(wikiPageArticleXML.title);
        BufferedWriter writer = writerMap.get(fileTag);

        for (String line : wikiPageArticleXML.lines) {
            writer.write(line);
            writer.write('\n');
        }
    }

    private static void writeFileHead(String fileTag) throws IOException {
        BufferedWriter writer = writerMap.get(fileTag);
        writer.write(
                "<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.10/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.10/ http://www.mediawiki.org/xml/export-0.10.xsd\" version=\"0.10\" xml:lang=\"en\">\n" +
                        "  <siteinfo>\n" +
                        "    <sitename>Wikipedia</sitename>\n" +
                        "    <dbname>enwiki</dbname>\n" +
                        "    <base>https://en.wikipedia.org/wzp.wiki/Main_Page</base>\n" +
                        "    <generator>MediaWiki 1.27.0-wmf.4</generator>\n" +
                        "    <case>first-letter</case>\n" +
                        "    <namespaces>\n" +
                        "      <namespace key=\"-2\" case=\"first-letter\">Media</namespace>\n" +
                        "      <namespace key=\"-1\" case=\"first-letter\">Special</namespace>\n" +
                        "      <namespace key=\"0\" case=\"first-letter\" />\n" +
                        "      <namespace key=\"1\" case=\"first-letter\">Talk</namespace>\n" +
                        "      <namespace key=\"2\" case=\"first-letter\">User</namespace>\n" +
                        "      <namespace key=\"3\" case=\"first-letter\">User talk</namespace>\n" +
                        "      <namespace key=\"4\" case=\"first-letter\">Wikipedia</namespace>\n" +
                        "      <namespace key=\"5\" case=\"first-letter\">Wikipedia talk</namespace>\n" +
                        "      <namespace key=\"6\" case=\"first-letter\">File</namespace>\n" +
                        "      <namespace key=\"7\" case=\"first-letter\">File talk</namespace>\n" +
                        "      <namespace key=\"8\" case=\"first-letter\">MediaWiki</namespace>\n" +
                        "      <namespace key=\"9\" case=\"first-letter\">MediaWiki talk</namespace>\n" +
                        "      <namespace key=\"10\" case=\"first-letter\">Template</namespace>\n" +
                        "      <namespace key=\"11\" case=\"first-letter\">Template talk</namespace>\n" +
                        "      <namespace key=\"12\" case=\"first-letter\">Help</namespace>\n" +
                        "      <namespace key=\"13\" case=\"first-letter\">Help talk</namespace>\n" +
                        "      <namespace key=\"14\" case=\"first-letter\">Category</namespace>\n" +
                        "      <namespace key=\"15\" case=\"first-letter\">Category talk</namespace>\n" +
                        "      <namespace key=\"100\" case=\"first-letter\">Portal</namespace>\n" +
                        "      <namespace key=\"101\" case=\"first-letter\">Portal talk</namespace>\n" +
                        "      <namespace key=\"108\" case=\"first-letter\">Book</namespace>\n" +
                        "      <namespace key=\"109\" case=\"first-letter\">Book talk</namespace>\n" +
                        "      <namespace key=\"118\" case=\"first-letter\">Draft</namespace>\n" +
                        "      <namespace key=\"119\" case=\"first-letter\">Draft talk</namespace>\n" +
                        "      <namespace key=\"446\" case=\"first-letter\">Education Program</namespace>\n" +
                        "      <namespace key=\"447\" case=\"first-letter\">Education Program talk</namespace>\n" +
                        "      <namespace key=\"710\" case=\"first-letter\">TimedText</namespace>\n" +
                        "      <namespace key=\"711\" case=\"first-letter\">TimedText talk</namespace>\n" +
                        "      <namespace key=\"828\" case=\"first-letter\">Module</namespace>\n" +
                        "      <namespace key=\"829\" case=\"first-letter\">Module talk</namespace>\n" +
                        "      <namespace key=\"2300\" case=\"first-letter\">Gadget</namespace>\n" +
                        "      <namespace key=\"2301\" case=\"first-letter\">Gadget talk</namespace>\n" +
                        "      <namespace key=\"2302\" case=\"case-sensitive\">Gadget definition</namespace>\n" +
                        "      <namespace key=\"2303\" case=\"case-sensitive\">Gadget definition talk</namespace>\n" +
                        "      <namespace key=\"2600\" case=\"first-letter\">Topic</namespace>\n" +
                        "    </namespaces>\n" +
                        "  </siteinfo>\n"
        );
    }

    private static void writeFileTail(String fileTag) throws IOException {
        BufferedWriter writer = writerMap.get(fileTag);
        writer.write(
                "\n</mediawiki>\n"
        );
    }

    private static void closeReader() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private static void closeWriters() {
        try {
            for(BufferedWriter writer : writerMap.values()) {
                writer.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
