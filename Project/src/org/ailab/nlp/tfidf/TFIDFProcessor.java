package org.ailab.nlp.tfidf;

import org.ailab.ml.WordVector;
import org.ailab.nlp.Stopwords;
import org.ailab.nlp.annotation.IAnnotation;
import org.ailab.nlp.parsing.TokenAnnotation;
import org.ailab.nlp.parsing.chinese.ChineseParser;
import org.ailab.tem.DBConfig;
import org.ailab.wimfra.database.DBUtilInstance;
import org.ailab.wimfra.util.CountTable;
import org.ailab.wimfra.util.Int;
import org.ailab.wimfra.util.KeyValue;
import org.ailab.wimfra.util.StringUtil;

import java.util.*;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午3:33
 * Desc:
 */
public class TFIDFProcessor {
    protected IDFDict idfDict;
    protected TFProcessor tfProcessor;
    protected Map<String, WordVector> docTextToWordTFIDFVectorMap = new HashMap<String, WordVector>();

    public TFIDFProcessor(IDFDict idfDict, TFProcessor tfProcessor) {
        this.idfDict = idfDict;
        this.tfProcessor = tfProcessor;
    }

    /**
     * 走一道缓存
     * @param docText
     * @return
     */
    public WordVector getWordTFIDFVector(String docText) {
        WordVector wordVector = docTextToWordTFIDFVectorMap.get(docText);
        if(wordVector == null) {
            wordVector = createWordTFIDFVector(docText);
        }
        return wordVector;
    }

    /**
     * 不管缓存，直接创建
     * @param docText
     * @return
     */
    protected WordVector createWordTFIDFVector(String docText) {
        // 获得词频向量
        WordVector wordTFVector = tfProcessor.getWordTFVector(docText);

        // 初始化TFIDF向量
        WordVector wordTFIDFVector = new WordVector(wordTFVector.size());

        // 对词频向量循环
        for(Map.Entry<String, Double> entry : wordTFVector.entrySet()) {
            String word = entry.getKey();
            double tf = entry.getValue();
            double idf = idfDict.getIDF(word);
            double tfidf = tf * idf;
            wordTFIDFVector.put(word, tfidf);
        }

        return wordTFIDFVector;
    }

}
