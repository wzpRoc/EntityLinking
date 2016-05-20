package org.ailab.tool.aida;

import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 15-12-18
 * Time: 下午8:27
 * To change this template use File | Settings | File Templates.
 */
public class AIDADatasetParser {
    enum MentionStatus {
        MentionBegin,
        MentionIn,
        MentionEnd,
        MentionOut
    };

    public static void main(String[] args) throws Exception {
        List<Doc> docList = new ArrayList<Doc>();
        List<Mention> mentionList = new ArrayList<Mention>();

        FileInputStream fis = null;

        InputStreamReader isr = null;

        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            Doc doc = new Doc();
            Mention mention = new Mention();

            int lineNum = 0;    //用来记录行号

            int docLineNum = 0; //记录当前doc起始行号

            int docNum = 0;     //记录doc表的doc编号

            int docId = 0;      //记录mention表的doc编号

            String tokens = "";

            String str = "";    //记录读取到的每一行字符串

            int seq = 0;        //记录mention的编号

            int startIndex = 0; //记录mention开始的行号

            int endIndex = 0;   //记录mention结束的行号

            String name = "";   //保存每一个mention的内容（token1 token2 ...）

            int entityId = 0;   //记录实体编号

            String tittle = ""; //记录每一个mention的tittle

            MentionStatus mentionStatus = MentionStatus.MentionEnd;   //记录当前mention的状态

            fis = new FileInputStream("D://AIDA-YAGO2-dataset.tsv");// FileInputStream

            // 从文件系统中的某个文件中获取字节

            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,

            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象

            while ((str = br.readLine()) != null) {
                lineNum++;
                if (str.contains("DOCSTART"))
                {
                    if(docNum != 0)
                    {
                        doc = new Doc();
                        doc.setId(docNum);
                        doc.setTokens(tokens);
                        docList.add(doc);

                        System.out.println("dcoNum:" + doc.getId() + "\t" + "tokens:" + doc.getTokens());
                        System.out.println("-----------------------------------------");
                    }

                    docNum++;
                    docLineNum = lineNum;
                    String[] docStartArray = str.split(" ");    //得到含有docStart文本的哪一行字符串然后用空格进行分割
                    String temp = docStartArray[1].substring(1, docStartArray[1].length());
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(temp);
                    docNum = Integer.parseInt(m.replaceAll("").trim());
                    docId = docNum;

                    tokens = "";                                 //清空tokens
                    seq = 0;
                }
                else
                {
                    String[] strArray = str.split("\t");
                    String token = strArray[0];                 //取出每一行的第一个单词
                    tokens = tokens + "\n" + token;             //将第一个单词拼接起来当作tokens
                    if (strArray.length >= 2)
                    {
                        if (strArray[1].equals("B"))
                        {
                            if(mentionStatus == MentionStatus.MentionBegin)
                            {
                                endIndex = lineNum - docLineNum;
                                mention = new Mention();

                                mention.setDocId(docId);
                                mention.setSeqInDoc(seq);
                                mention.setStartIdx(startIndex);
                                mention.setEndIdx(endIndex);
                                mention.setName(name.trim());
                                mention.setWikiTitle(tittle);
                                mention.setEntityId(entityId);
                                mentionList.add(mention);

                                System.out.println("docId:" + docId + "\tseqInDoc:" + seq + "\tstartIndex:" + startIndex + "\tendIndex:" + endIndex + "\tname:" + name + "\ttittle:" + tittle + "\tentityId:" + entityId);
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                                tittle = "";
                                entityId = 0;
                                name = "";
                                //--------------------------------------------------

                                mentionStatus = MentionStatus.MentionBegin;
                            }
                            else
                            {
                                mentionStatus = MentionStatus.MentionBegin;
                            }

                        }
                        else if (strArray[1].equals("I"))
                        {
                            mentionStatus = MentionStatus.MentionIn;
                        }
                    }
                    else
                    {
                        if(mentionStatus == MentionStatus.MentionEnd)
                        {
                            mentionStatus = MentionStatus.MentionOut;
                        }
                        else if(mentionStatus == MentionStatus.MentionBegin || mentionStatus == MentionStatus.MentionIn)
                        {
                            mentionStatus = MentionStatus.MentionEnd;
                        }
                    }

                    if (mentionStatus == MentionStatus.MentionBegin)
                    {
                        seq++;
                        startIndex = lineNum - docLineNum;
                        name = name + " " + token ;

                        if(strArray.length >= 6)
                        {
                            if (strArray[4].contains("http://"))
                            {
                                tittle = strArray[4].substring(strArray[4].lastIndexOf("/") + 1, strArray[4].length());
                            }
                            entityId = Integer.parseInt(strArray[5]);
                        }
                        else
                        {
                            tittle = "";
                            entityId = 0;
                        }
                    }
                    else if (mentionStatus == MentionStatus.MentionIn)
                    {
                        name = name + " " + token ;
                    }
                    else if (mentionStatus == MentionStatus.MentionEnd)
                    {
                        endIndex = lineNum - docLineNum;
                        mention = new Mention();

                        mention.setDocId(docId);
                        mention.setSeqInDoc(seq);
                        mention.setStartIdx(startIndex);
                        mention.setEndIdx(endIndex);
                        mention.setName(name.trim());
                        mention.setWikiTitle(tittle);
                        mention.setEntityId(entityId);
                        mentionList.add(mention);

                        System.out.println("docId:" + docId + "\tseqInDoc:" + seq + "\tstartIndex:" + startIndex + "\tendIndex:" + endIndex + "\tname:" + name + "\ttittle:" + tittle + "\tentityId:" + entityId);
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                        tittle = "";
                        entityId = 0;
                        name = "";

                    }
                    else if(mentionStatus == MentionStatus.MentionOut)
                    {
                        name = "";
                    }


                }

            }

            // 当读取的一行不为空时,把读到的str的值赋给str1

        } catch (FileNotFoundException e) {

            System.out.println("找不到指定文件");

        } catch (IOException e) {

            System.out.println("读取文件失败");

        } finally {

            try {

                br.close();

                isr.close();

                fis.close();

                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        for(int i=0; i<docList.size(); i++)
        {
            Doc doc = docList.get(i);
            doc.setId(i+1);
        }
        (new DocBL()).batchInsert(docList);

        for(int i=0; i<mentionList.size(); i++)
        {
            Mention mention = mentionList.get(i);
            mention.setId(i+1);
        }
        (new MentionBL()).batchInsert(mentionList);
    }
}
