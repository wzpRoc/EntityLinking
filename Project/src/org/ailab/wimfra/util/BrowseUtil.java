package org.ailab.wimfra.util;

import java.io.File;
import java.net.URI;

/**
 * User: Lu Tingming
 * Date: 2010-11-16 18:27:44
 * Desc: 用于打开网页和本地文件的类
 */
public class BrowseUtil {
    /**
     * 打开本地文件
     * @param path
     * @throws Exception
     */
    public static void browseLocalFile(String path) throws Exception {
        browse((new File(path)).toURI());
    }

    /**
     * 打开网页
     * @param url
     * @throws Exception
     */
    public static void browseUrl(String url) throws Exception {
        browse(URI.create(url));
    }

    /**
     * 打开URI
     * @param uri
     * @throws Exception
     */
    public static void browse(URI uri) throws Exception {
        //判断当前系统是否支持Java AWT Desktop扩展
        if (!java.awt.Desktop.isDesktopSupported()) {
            throw new Exception("Java AWT Desktop is not supported.");
        }
        //获取当前系统桌面扩展
        java.awt.Desktop dp = java.awt.Desktop.getDesktop();
        //判断系统桌面是否支持要执行的功能
        if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
        //获取系统默认浏览器打开链接
            dp.browse(uri);
        } else {
            throw new Exception("Desktop does nos support BROWSE operation.");
        }
    }
}
