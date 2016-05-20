package org.ailab.wimfra.codeAnalysis;

import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-10-31
 * Time: 上午9:22
 */
public class CodeAnalyzer {
    public static List<FileInfo> fiList;
    private static String baseDir;

    public static void analyze(File f) throws Exception {
        final String path = f.getPath();
        final String name = f.getName();
        if(name.startsWith(".")) {
            return;
        }
        System.out.println("analyzing " + path);

        if(f.isDirectory()) {
            if("out".equals(name) || "output".equals(name)) {
                return;
            }
            File[] files = f.listFiles();
            if(files != null) {
                for(File sf : files) {
                    analyze(sf);
                }
            }
        } else {
            // file
            String extName = FileUtil.extNameFromName(f.getName());
            if("jar".equalsIgnoreCase(extName)
                    || "gz".equalsIgnoreCase(extName)
                    || "zip".equalsIgnoreCase(extName)
                    || "class".equalsIgnoreCase(extName)
                    || "jpg".equalsIgnoreCase(extName)
                    || "jpeg".equalsIgnoreCase(extName)
                    || "png".equalsIgnoreCase(extName)
                    || "svg".equalsIgnoreCase(extName)
                    || "pdf".equalsIgnoreCase(extName)
                    || "gif".equalsIgnoreCase(extName)
                    ) {
                return;
            }
            String relativePath = path.substring(baseDir.length());
            FileInfo fi = FileInfo.create(relativePath, f);
            fiList.add(fi);
        }
    }


    public static void main(String[] args) throws Exception {
        baseDir = "D:\\Projects\\DCDT\\Project\\HITZD_HIS_C\\CommonLib\\";
//        baseDir = "D:\\IRICA\\Project\\src\\data\\opinionMining";
        String tableName = "fileinfo_hitzd_common";

        fiList = new ArrayList<FileInfo>();
        analyze(new File(baseDir));

        DBUtil.truncate(tableName);
        DBUtil.batchInsert(tableName, fiList);
    }
}
