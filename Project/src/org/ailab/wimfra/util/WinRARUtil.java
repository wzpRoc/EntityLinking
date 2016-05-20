package org.ailab.wimfra.util;

import java.io.IOException;

/**
 * User: Lu Tingming
 * Date: 2011-1-12
 * Time: 15:20:13
 * Desc: 压缩工具
 */
public class WinRARUtil {
    // 程序的路径
    public static String rarPath = "D:\\Progra~1\\WinRAR\\WinRAR.exe";

    /**
     * 压缩文件
     *
     * @param source       可以是目录，也可以是文件
     * @param deleteSource 是否删除源目录/文件
     */
    public static Process compress(String source, boolean deleteSource) throws IOException, InterruptedException {
        if(source.endsWith("\\")){
            source = source.substring(0, source.length()-1);
        }
        String dest = source + ".rar";
        StringBuffer cmd = new StringBuffer(rarPath).append(" A -r -ep1 -ibck ");
        if (deleteSource) {
            cmd.append("-df ");
        }
        cmd.append(dest).append(" ").append(source);
        return Runtime.getRuntime().exec(cmd.toString());
    }

    /**
     * wzp.newsML.test
     *
     * @param args
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        compress("E:\\_Test\\d", true);
    }
}
