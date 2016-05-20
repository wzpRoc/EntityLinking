package org.ailab.tool.aida;

import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 15-12-20
 * Time: 下午10:42
 * To change this template use File | Settings | File Templates.
 */
public class DataSetExtractor {
    public static void main(String[] args) throws Exception {
        List<String> lines = FileUtil.readLines("D:\\AIDA-YAGO2-dataset.tsv");
        List<Mention> mentionList = new ArrayList<Mention>();
        List<Doc> docList = new ArrayList<Doc>();
        int seq = 0;
        int docNum = 0;
        int docLineNum = 0;
        for(int i=0; i<lines.size(); i++) {
            int lineNum = i;
            String line = lines.get(i);

            if(line.contains("DOCSTART"))
            {
                docLineNum = lineNum;
                Doc doc = new Doc();
                StringBuilder tokens_sb = new StringBuilder();

                String[] docStartArray = line.split(" ");    //得到含有docStart文本的哪一行字符串然后用空格进行分割
                String temp = docStartArray[1].substring(1, docStartArray[1].length());
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(temp);
                docNum = Integer.parseInt(m.replaceAll("").trim());

                for(int j=i+1; j<lines.size(); j++)
                {
                    String line_j = lines.get(j);

                    if(line_j.contains("DOCSTART"))
                    {
                        break;
                    }
                    else
                    {
                        String[] as_j = line_j.split("\t");
                        if (as_j.length >= 1)
                        {
                            String token_j = as_j[0];
                            if(tokens_sb.length() != 0)
                            {
                                tokens_sb.append("\n" + token_j);
                            }
                            else
                            {
                                tokens_sb.append(token_j);
                            }
                        }
                    }
                }

                doc.setId(docNum);
                doc.setTokens(tokens_sb.toString());
                docList.add(doc);

                seq = 0;

            }
            else
            {
                String[] as = line.split("\t");
                 if (as.length > 1) {
                    String token = as[0];
                    String tag = as[1];
                    String tittle = null;
                    int entityId = 0;
                    if ("B".equals(tag)) {
                        int startIndex = lineNum - docLineNum;
                        int endIndex = 0;
                        seq++;
                        Mention mention = new Mention();
                        // mention start
                        if(as.length >= 6)
                        {
                            if (as[4].contains("http://"))
                            {
                                tittle = as[4].substring(as[4].lastIndexOf("/") + 1, as[4].length());
                            }
                            entityId = Integer.parseInt(as[5]);
                        }
                        else
                        {
                            tittle = "";
                            entityId = 0;
                        }

                        StringBuilder sb = new StringBuilder();
                        sb.append(token);
                        for (int j = i + 1; j < lines.size(); j++) {
                            String line_j = lines.get(j);
                            String[] as_j = line_j.split("\t");
                            if (as_j.length > 1) {
                                String token_j = as_j[0];
                                String tag_j = as_j[1];
                                if ("I".equals(tag_j)) {
                                    // mention continue
                                    sb.append(" " + token_j);
                                } else {
                                    endIndex = j - docLineNum;
                                    break;
                                }
                            } else {
                                endIndex = j - docLineNum;
                                break;
                            }
                            endIndex = j - docLineNum;
                        }

                        mention.setDocId(docNum);
                        mention.setSeqInDoc(seq);
                        mention.setStartIdx(startIndex);
                        mention.setEndIdx(endIndex);
                        mention.setName(sb.toString());
                        mention.setWikiTitle(tittle);
                        mention.setEntityId(entityId);
                        mentionList.add(mention);
                        // System.out.println(mentionList.size() + "\t" + mention);
                    }
                }
            }
        }

        DBUtil.executeUpdate("truncate table doc");
        for(int i=0; i<docList.size(); i++)
        {
            Doc doc = docList.get(i);
            doc.setId(i+1);
        }
        (new DocBL()).batchInsert(docList);

        DBUtil.executeUpdate("truncate table mention");
        for(int i=0; i<mentionList.size(); i++)
        {
            Mention mention = mentionList.get(i);
            mention.setId(i+1);
        }
        (new MentionBL()).batchInsert(mentionList);

        DBUtil.executeUpdate("UPDATE el.mention m SET m.name=REPLACE(m.name, ' ''', '''') WHERE m.name LIKE '% ''%';");
        DBUtil.executeUpdate("UPDATE el.mention m SET m.name=REPLACE(m.name, ' )', ')') WHERE m.name LIKE '% )%';");
        DBUtil.executeUpdate("UPDATE el.mention m SET m.name=REPLACE(m.name, '( ', '(') WHERE m.name LIKE '%( %';");
        DBUtil.executeUpdate("UPDATE el.mention m SET m.name=REPLACE(m.name, ' / ', '/') WHERE m.name LIKE '% / %';");
        DBUtil.executeUpdate("UPDATE el.mention m SET m.name=REPLACE(m.name, ' / ', '/') WHERE m.name LIKE '% / %';");

//        DBUtil.executeUpdate("truncate table t");
//        DBUtil.batchExecuteWithValueCollection("insert into t values(?)", mentionList);

/*
        Set<String> set = new HashSet<String>();
        for(String mention : mentionList) {
            set.add(mention);
        }

        for(String mention : set) {
            System.out.println(mention);
        }
*/



    }
}
