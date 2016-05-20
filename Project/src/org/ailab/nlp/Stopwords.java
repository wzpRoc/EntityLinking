package org.ailab.nlp;

import org.ailab.wimfra.util.FileUtil;

import java.util.List;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午4:22
 * Desc:
 */
public class Stopwords {
    private static Set<String> set;

    static {
        set = FileUtil.readLinesIntoHashSet("D:\\Projects\\EntityLinking\\Project\\resource\\stopwords.txt");
    }

    public static boolean isStopword(String word) {
        return set.contains(word);
    }
}
