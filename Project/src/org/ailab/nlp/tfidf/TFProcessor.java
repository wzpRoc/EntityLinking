package org.ailab.nlp.tfidf;

import org.ailab.ml.WordVector;
import org.ailab.nlp.Stopwords;
import org.ailab.nlp.parsing.TokenAnnotation;
import org.ailab.nlp.parsing.chinese.ChineseParser;
import org.ailab.wimfra.util.CountTable;
import org.ailab.wimfra.util.Int;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午6:42
 * Desc: 文档的词频向量缓存
 * 只要分词方式一致，那么词频向量只与输入的文本有关
 * 而IDF与背景语料库有关
 */
public class TFProcessor {
    protected static TFProcessor instance;
    protected Map<String, WordVector> docTextToWordTFVectorMap = new HashMap<String, WordVector>();

    public static TFProcessor getInstance() {
        if(instance == null) {
            instance = new TFProcessor();
        }
        return instance;
    }

    /**
     * 走一道缓存
     * @param docText
     * @return
     */
    public WordVector getWordTFVector(String docText) {
        WordVector wordVector = docTextToWordTFVectorMap.get(docText);
        if(wordVector == null) {
            wordVector = createWordTFVector(docText);
        }
        return wordVector;
    }

    /**
     * 不管缓存，直接创建
     * @param docText
     * @return
     */
    protected WordVector createWordTFVector(String docText) {
        List<TokenAnnotation> tokenAnnotationList = ChineseParser.parse(docText);
        return createWordTFVector(tokenAnnotationList);
    }

    protected CountTable countNonstopwords(List<TokenAnnotation> tokenAnnotationList) {
        CountTable ct = new CountTable();
        for(TokenAnnotation token : tokenAnnotationList) {
            String word = token.getText();
            if(!Stopwords.isStopword(word)) {
                ct.add(word);
            }
        }

        return ct;
    }


    protected WordVector createWordTFVector(List<TokenAnnotation> tokenAnnotationList) {
        CountTable wordOccurCounter = countNonstopwords(tokenAnnotationList);

        WordVector wordTFVector = new WordVector(wordOccurCounter.size());
        for(Map.Entry<Object, Int> entry : wordOccurCounter.entrySet()) {
            String word = (String) entry.getKey();
            int wordOccurCount = entry.getValue().i;
            double tf = (double) wordOccurCount / wordOccurCounter.getTotal();
            wordTFVector.put(word, tf);
        }

        return wordTFVector;
    }
}
