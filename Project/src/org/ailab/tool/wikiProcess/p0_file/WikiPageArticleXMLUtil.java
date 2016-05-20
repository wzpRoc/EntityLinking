package org.ailab.tool.wikiProcess.p0_file;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lutingming
 * Date: 16-1-4
 * Time: 下午3:33
 */
public class WikiPageArticleXMLUtil {
    public static final String DIGITS_FILE_TAG = "0-9";
    public static final String OTHER_FILE_TAG = "_Other";
    public static final List<String> fileTagList;

    static {
        fileTagList = new ArrayList<String>();
        for (char c = 'A'; c <= 'Z'; c++) {
            String fileTag = WikiPageArticleXMLUtil.getFileTag(c);
            fileTagList.add(fileTag);
        }
        fileTagList.add(WikiPageArticleXMLUtil.DIGITS_FILE_TAG);
        fileTagList.add(WikiPageArticleXMLUtil.OTHER_FILE_TAG);
    }

    public static String getFileTag(String title) {
        char firstChar = title.charAt(0);
        firstChar = Character.toUpperCase(firstChar);

        return getFileTag(firstChar);
    }

    public static String getFileTag(char firstChar) {
        String fileTag;
        if (firstChar >= 'A' && firstChar <= 'Z') {
            fileTag = new String(new char[]{firstChar});
        } else if (firstChar >= '0' && firstChar <= '9') {
            fileTag = DIGITS_FILE_TAG;
        } else {
            fileTag = OTHER_FILE_TAG;
        }
        return fileTag;
    }


}
