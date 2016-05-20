package org.ailab.entityLinking.wim.docWordFreq;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-12-29
 * Time: 下午9:40
 * To change this template use File | Settings | File Templates.
 */
public class DocWordFreqDict {
    private static HashMap<String, DocWordFreq> docWordFreqHashMap = null;

    private static DocWordFreqDict docWordFreqDict = null;

    private DocWordFreqDict() {
        docWordFreqHashMap = new HashMap<String, DocWordFreq>();
        DocWordFreqBL docWordFreqBL = new DocWordFreqBL();
        List<DocWordFreq> docWordFreqList = docWordFreqBL.getList();
        for (DocWordFreq docWordFreq : docWordFreqList) {
            docWordFreqHashMap.put(docWordFreq.getWordName(), docWordFreq);
        }
    }

    public static synchronized DocWordFreqDict getNewInstance() {
        if(docWordFreqDict == null) {
            docWordFreqDict = new DocWordFreqDict();
        }
        return docWordFreqDict;
    }

    public DocWordFreq getDocWordFreqByName(String mentionName) {
        if (docWordFreqHashMap.containsKey(mentionName))
            return docWordFreqHashMap.get(mentionName);
        return null;
    }
}
