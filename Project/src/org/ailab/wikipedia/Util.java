package org.ailab.wikipedia;

import org.ailab.wimfra.util.StringUtil;

import java.io.File;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-2
 * Time: 下午6:25
 */
public class Util {
    /**
     */
    public static String getArticlePath(String downloadBaseDir, String articleLink) {
        // http://zh.wikipedia.org/wzp.wiki/%E9%99%B3%E5%A5%95%E8%BF%85
        String s = StringUtil.extractFromExclude(articleLink, "/wzp/wiki/");
        s = s
                .replace("\"", "%22")
                .replace("²", "%C2%B2")
                .replace("?", "%3F")
        ;
        return downloadBaseDir + "/Article/" + s +".htm";
    }


    public static void main(String[] args) {
        String dirStr="D:\\Wikipedia\\artist\\Article\\";
        rename(new File(dirStr));
    }

    private static void rename(File dir ) {
        final File[] files = dir.listFiles();
        if (files != null) {
            for(File oldFile : files) {
                if(oldFile.isDirectory()) {
                    rename(oldFile);
                }
                final String oldPath = oldFile.getPath();
                final String newPath = URLDecoder.decode(oldPath);
                if(newPath.equals(oldPath)) {
                    continue;
                }
                File newFile = new File(newPath);
                oldFile.renameTo(newFile);
            }
        }
    }
}
