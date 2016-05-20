package org.ailab.tool;

import org.ailab.tool.aida.Abstract;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 15-12-29
 * Time: 下午8:42
 */
public class WikiAbstProcessor {
    public static void main(String[] args) throws Exception {
//        List<String> titleList = new ArrayList<String>();
        for (int i=1; i<=25; i++) {
            System.out.println("----------------------- processing file "+i);
            String fileName="D:\\Wikipedia\\en20151102\\abstracts\\enwiki-20151102-abstract"+i+".xml";
            List<String> lines = FileUtil.readLines(fileName);
            List<Abstract> abstractList = new ArrayList<Abstract>();
            for(int j=0; j<lines.size(); j++) {
                if(j%100000==0) {
                    System.out.println("processing file "+i +"\tline"+j);
                }
                String line = lines.get(j);
                if(line.startsWith("<url>")) {
                    String title = line.substring(35, line.length()-6);
                    if(title.contains("%")) {
                        title = URLDecoder.decode(title, "UTF-8");
                        title = StringEscapeUtils.unescapeHtml(title);
                    }
                    // 处理下一行
                    String nextLine = lines.get(j+1);
                    if(nextLine.startsWith("<abstract>")) {
                        if(!nextLine.endsWith("</abstract>")) {
                            throw new Exception("abstract line is not end with </abstract>: \n"+nextLine);
                        }
                        String abst = nextLine.replace("<abstract>", "").replace("</abstract>", "");
//                        abst = URLDecoder.decode(abst, "UTF-8");
                        abst = StringEscapeUtils.unescapeHtml(abst);
                        if(abst!=null
                            && abst.length()>=9
                            && "#REDIRECT".equalsIgnoreCase(abst.substring(0, 9))) {
                            // do nothing
                        } else {
                            // ok
                            Abstract abstObj = new Abstract();
                            abstObj.title=title;
                            abstObj.abst=abst;
                            abstractList.add(abstObj);
                        }
                    } else {
                        System.out.println("next line is not abstract: "+nextLine);
                    }
                }
            }
            System.out.println("-----------------------------   save file "+i);
            DBUtil.batchInsert("page_abst", abstractList);
        }

//        FileUtil.writeLines(titleList, "d:\\wikiTitles.txt");
    }
}
