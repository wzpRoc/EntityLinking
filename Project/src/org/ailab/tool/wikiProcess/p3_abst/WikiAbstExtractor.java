package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.wimfra.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc: 维基百科页面中摘要抽取器
 */
public class WikiAbstExtractor {

    private static final Pattern pattern = Pattern.compile("\\n==.+==");

    /**
     * 获得摘要
     */
    public static String getAbst(String plainText) {
        if(plainText == null) {
            return null;
        }

        String firstLine = StringUtil.extractFirstLine(plainText);
        if(firstLine == null) {
            return plainText;
        }

        if(firstLine.startsWith("==") && plainText.length()>firstLine.length()) {
            plainText = plainText.substring(firstLine.length()+1);
        }

        for(int n=1; n<=100; n++) {
            String lines = StringUtil.extractFirstNLines(plainText, n);
            if(lines == null) {
                return plainText;
            } else if(lines.length()<25) {
                continue;
            } else {
                return lines;
            }
        }

        return plainText;
    }


    /**
     * 获得长摘要
     */
    public static String getLongAbst(String plainText) {
        if(plainText == null) {
            return null;
        }

        String firstLine = StringUtil.extractFirstLine(plainText);
        if(firstLine == null) {
            // 只有一行
            return plainText;
        }

        // 如果第一行以“==”开头，去除第一行
        if(firstLine.startsWith("==") && plainText.length()>firstLine.length()) {
            plainText = plainText.substring(firstLine.length()+1);
        }

        // 找到下一个“==”
        Matcher matcher = pattern.matcher(plainText);
        if(!matcher.find()) {
            // 没有找到
        } else {
            int startIdxOfChapter = matcher.start();
            if(startIdxOfChapter<=0) {
                // 会有这种情况吗？
                System.err.println("startIdxOfChapter="+startIdxOfChapter);
            } else {
                return plainText.substring(0, startIdxOfChapter);
            }
        }

        // 一行一行往下找，直到累满3000字
        int minChars = 3000;
        if(plainText.length()<=minChars) {
            //
        } else {
            // 从3000开始，往后寻找第一个换行
            int firstEOLAfterMinChars = plainText.indexOf('\n', minChars);
            if(firstEOLAfterMinChars <=0) {
                //
            } else {
                return plainText.substring(0, firstEOLAfterMinChars);
            }
        }

        return plainText;
    }
}