package org.ailab.wimfra.util;

import java.io.*;

/**
 * User: Lu Tingming
 * Date: 15-5-6
 * Time: 下午12:07
 * Desc:
 */
public class FileSplitter {
    String srcFilePath;
    int linesPerDesFile;

    private String desFileDir;
    private String mainName;
    private String extName;

    public FileSplitter(String srcFilePath, int linesPerDesFile) {
        this.srcFilePath = srcFilePath;
        this.linesPerDesFile = linesPerDesFile;
    }

    /**
     * 分割大文件为小文件
     */
    public void split() {
        mainName = FileUtil.mainNameFromName(srcFilePath);
        extName = FileUtil.extNameFromName(srcFilePath);

        desFileDir = FileUtil.getDir(srcFilePath);
        desFileDir += mainName + "_split";
        FileUtil.mkdirIfNotExist(desFileDir);

        BufferedReader reader = null;
        BufferedWriter writer = null;

        int desLineCount = 0;
        int desFileCount = 0;
        int srcLineCount = 0;

        try {
            File srcFile = new File(srcFilePath);
            reader = new BufferedReader(new FileReader(srcFile));

            for (srcLineCount = 0; true; srcLineCount++) {
                String strLine = reader.readLine();
                if (strLine == null) break;

                if (desLineCount == 0) {
                    System.out.println(srcLineCount+" lines read");
                    int desFileIndex = srcLineCount / linesPerDesFile;
                    System.out.println("create file "+desFileIndex);
                    writer = createWriter(desFileIndex);
                    desFileCount++;
                }
                desLineCount++;

                writer.write(strLine);
                writer.write('\n');

                if (desLineCount == linesPerDesFile) {
                    System.out.println("close writer");
                    releaseWriter(writer);
                    desLineCount = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseReader(reader);
            releaseWriter(writer);
        }

        System.out.println("srcFilePath=" + srcFilePath);
        System.out.println("srcFileLine=" + srcLineCount);
        System.out.println("desFileCount=" + desFileCount);
    }

    private BufferedWriter createWriter(int desFileIndex) throws IOException {
        String desFilePath = getDesFilePath(desFileIndex);
        File desFile = new File(desFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(desFile));
        return writer;
    }

    private String getDesFilePath(int desFileIndex) {
        String desFileIndexFormatted = StringUtil.formatNumber(desFileIndex, 5);

        if (extName == null) {
            return desFileDir + "/" + mainName + "." + desFileIndexFormatted;
        } else {
            return desFileDir + "/" + mainName + "." + desFileIndexFormatted + "." + extName;
        }
    }

    private void releaseReader(BufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void releaseWriter(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
