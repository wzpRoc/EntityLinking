package org.ailab.tool.nlpccKBImporter;

import org.ailab.entityLinking_old.wim.wikiFact.WikiFact;
import org.ailab.entityLinking_old.wim.wikiFact.WikiFactBL;
import org.ailab.wimfra.util.FileUtil;
import org.ailab.wimfra.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-6
 * Time: 上午11:38
 * Desc: 用于导入NLPCC知识库
 */
public class NLPCCKBImporter {
    private final int valueCountInLine = 6;
    private final int maxLengthOfObjectName = 2000;
    private WikiFactBL wikiFactBL;

    public NLPCCKBImporter() {
        wikiFactBL = new WikiFactBL();
    }

    public int importFromDir(File dir, int startId) throws Exception {
        int totalRecord = 0;
        File[] files = dir.listFiles();
        for(int i=0; i<files.length; i++) {
            File file = files[i];
            System.out.println("processing file "+(i+1)+"/"+files.length+"\t"+file.getName());
            int recordCountOfFile = importFromFile(file, startId);
            totalRecord += recordCountOfFile;
            startId += recordCountOfFile;
            System.out.println(totalRecord+" records imported");
        }

        return totalRecord;
    }

    public List<WikiFact> loadFromFile(File file, int startId) throws Exception {
        List<String> lineList = FileUtil.readLines(file);
        if(lineList == null) {
            return null;
        }

        List<WikiFact> list = new ArrayList<WikiFact>(lineList.size());
        int recordIdx = 0;
        for(String line : lineList) {
            if(StringUtil.isEmpty(line)) {
                continue;
            }

            WikiFact wikiFact = lineToObject(line);
            wikiFact.setId(startId+recordIdx+1);
            list.add(wikiFact);

            recordIdx++;
        }

        return list;
    }

    public int importFromFile(File file, int startId) throws Exception {
        List<WikiFact> wikiFactList = loadFromFile(file, startId);
        int recordCount = wikiFactBL.batchInsert(wikiFactList);

        return recordCount;
    }

    private WikiFact lineToObject(String line) {
        String[] array = lineToArray(line);

        WikiFact wikiFact = new WikiFact();

        wikiFact.setEntityId(Integer.parseInt(array[0]));
        wikiFact.setPredicateId(Integer.parseInt(array[1]));
        wikiFact.setObjectId(Integer.parseInt(array[2]));
        wikiFact.setEntityTitle(array[3]);
        wikiFact.setPredicateName(array[4]);
        wikiFact.setObjectName(array[5]);

        if(wikiFact.getObjectName()!=null && wikiFact.getObjectName().length()> maxLengthOfObjectName) {
            wikiFact.setObjectName(wikiFact.getObjectName().substring(0, maxLengthOfObjectName));
        }

        return wikiFact;
    }

    private String[] lineToArray(String line) {
        String[] array = new String[valueCountInLine];
        int idxOfLastTab = -1;
        for(int i=0; i< valueCountInLine - 1; i++) {
            int idxOfNextTab = line.indexOf('\t', idxOfLastTab+1);
            if(idxOfNextTab == -1) {
                throw new RuntimeException("invalid line : "+line);
            }

            array[i] = line.substring(idxOfLastTab+1, idxOfNextTab);
            idxOfLastTab = idxOfNextTab;
        }

        array[valueCountInLine-1] = line.substring(idxOfLastTab+1);

        return array;
    }

    public static void main(String[] args) throws Exception {
//        new NLPCCKBImporter().importFromFile(new File("D:\\Research\\NLPCC\\2015\\Sample Data\\releaseRDF_split\\releaseRDF.00086.dumps"));
        NLPCCKBImporter nlpcckbImporter = new NLPCCKBImporter();
        nlpcckbImporter.wikiFactBL.truncate();
        nlpcckbImporter.importFromDir(new File("D:\\Research\\NLPCC\\2015\\Sample Data\\releaseRDF_split"), 1);
    }
}
