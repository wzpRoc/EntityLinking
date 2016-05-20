package org.ailab.tool.aida;

import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 15-12-20
 * Time: 下午10:42
 * To change this template use File | Settings | File Templates.
 */
public class MentionExtractor {
    public static void main(String[] args) throws Exception {
        List<String> lines = FileUtil.readLines("D:\\AIDA-YAGO2-dataset.tsv");
        List<String> mentionList = new ArrayList<String>();

        for(int i=0; i<lines.size(); i++) {
            String line = lines.get(i);
            String[] as = line.split("\t");
            if(as.length>1) {
                String token = as[0];
                String tag = as[1];
                if("B".equals(tag)) {
                    // mention start
                    StringBuilder sb = new StringBuilder();
                    sb.append(token);
                    for(int j=i+1; j<lines.size(); j++) {
                        String line_j = lines.get(j);
                        String[] as_j = line_j.split("\t");
                        if(as_j.length>1) {
                            String token_j = as_j[0];
                            String tag_j = as_j[1];
                            if("I".equals(tag_j)) {
                                // mention continue
                                sb.append(" "+token_j);
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    String mention = sb.toString();
                    mentionList.add(mention);
                    // System.out.println(mentionList.size() + "\t" + mention);
                }
            }
        }

        DBUtil.executeUpdate("truncate table t");
        DBUtil.batchExecuteWithValueCollection("insert into t values(?)", mentionList);

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
