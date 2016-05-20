package org.ailab.nlp.tfidf;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午3:33
 * Desc:
 */
public class IDFDict {
    int docCount;
    Map<String, Double> wordToIDFMap;

    public IDFDict(Map<String, Double> wordToIDFMap, int docCount) {
        this.docCount = docCount;
        this.wordToIDFMap = wordToIDFMap;
    }

    public double getIDF(String word) {
        Double idf = wordToIDFMap.get(word);
        if(idf == null) {
            idf = IDFDictFactory.getIDF(docCount, 0);
        }

        return idf;
    }
}
