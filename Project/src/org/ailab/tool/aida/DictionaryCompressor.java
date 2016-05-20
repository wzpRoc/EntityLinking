package org.ailab.tool.aida;

import org.ailab.entityLinking.wim.entityName.EntityName;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.util.FileUtil;

import java.io.*;
import java.util.*;

/**
 * User: robby
 * Date: 15-12-21
 * Time: 下午8:03
 * Desc: 只抽取跟mention.name相关（不区分大小写匹配）的记录
 */
public class DictionaryCompressor {
    public static Set<String> nameSet = new HashSet<String>();

    public static void CreateSet() {
        List<Mention> mentionList = (new MentionBL()).getList();
        System.out.println("共读取了" + mentionList.size() + "条mention");
        for (Mention mention : mentionList) {
            // 不区分大小写
            nameSet.add(mention.getName().toLowerCase());
        }
    }

    public static void main(String args[]) throws FileNotFoundException {
        String target_path = "D:\\Datasets_of_EL\\GoogleCrossWikiDictionary\\compressedDictionary.txt";
        String original_path = "D:\\Datasets_of_EL\\GoogleCrossWikiDictionary\\dictionary";
        FileUtil.deleteFile(target_path);
        System.out.println("删除文件完毕");

        CreateSet();

        FileInputStream fis = new FileInputStream(original_path);   // FileInputStream
        // 从文件系统中的某个文件中获取字节
        InputStreamReader isr = new InputStreamReader(fis);                                 // InputStreamReader 是字节流通向字符流的桥梁,
        BufferedReader br = new BufferedReader(isr);                                        // 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
        String line;
        int lineNum = 0;
        BufferedWriter writer = null;
        try {
            File file = new File(target_path);
            writer = new BufferedWriter(new FileWriter(file, true));
            while ((line = br.readLine()) != null) {
                lineNum++;
                String strArray[] = line.split("\t");
                if (strArray.length >= 1) {
                    String name_dict = strArray[0];
                    // 不区分大小写
                    if (name_dict != null && !"".equals(name_dict) && nameSet.contains(name_dict.toLowerCase())) {
                        if (lineNum % 10000 == 0) {
                            System.out.println("第" + lineNum + "行文件内容为:" + line);
                        }

                        writer.write(line + "\n");
                        writer.flush();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}