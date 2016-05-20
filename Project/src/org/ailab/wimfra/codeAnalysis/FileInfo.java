package org.ailab.wimfra.codeAnalysis;

import org.ailab.wimfra.util.FileUtil;
import org.ailab.wimfra.util.StringUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-10-31
 * Time: 上午9:24
 */
public class FileInfo {
    public String relativePath;
    public String name;
    public String extName;
    public int depth;
    public long size;
    public int lineCount;
    public String dir0;
    public String dir1;
    public String dir2;
    public String dir3;
    public String dir4;
    public String dir5;
    public String dir6;


    public static FileInfo create(String relativePath, File f) throws Exception {
        if(relativePath.endsWith("/") || relativePath.endsWith("\\")) {
            // ok
        } else {
            relativePath = relativePath + FileUtil.FILE_SEPARATOR;
        }
        if(relativePath.startsWith("/") || relativePath.startsWith("\\")) {
            relativePath = relativePath.substring(1);
        } else {
            // ok
        }

        FileInfo fi = new FileInfo();
        fi.relativePath = relativePath;
        fi.name = f.getName();
        fi.extName = FileUtil.extNameFromName(fi.name);
        fi.depth = StringUtil.count(relativePath, "/") + StringUtil.count(relativePath, "\\\\");
        fi.size = f.length();
        fi.lineCount = FileUtil.countLines(f);

        String[] dirs = relativePath.split("[/\\\\]");
        String prefix = "";
        for(int i=0; i<=6; i++) {
            if(i<dirs.length) {
                final String dir = prefix + "/" + dirs[i];
                fi.setDir(i, dir);
                prefix = dir;
            } else {
                fi.setDir(i, "");
            }
        }

        return fi;
    }


    private void setDir(int depth, String dir) {
        switch (depth) {
            case 0: dir0 = dir; break;
            case 1: dir1 = dir; break;
            case 2: dir2 = dir; break;
            case 3: dir3 = dir; break;
            case 4: dir4 = dir; break;
            case 5: dir5 = dir; break;
            case 6: dir6 = dir; break;
        }
    }


    public static void main(String[] args) throws Exception {
        FileInfo fi = FileInfo.create("src\\org\\ailab\\irica", new File("D:\\IRICA\\Project\\src\\org\\ailab\\irica\\BusinessConstants.java"));
        System.out.println();
    }
}
