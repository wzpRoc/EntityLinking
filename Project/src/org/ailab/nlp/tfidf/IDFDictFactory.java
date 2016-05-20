package org.ailab.nlp.tfidf;

import org.ailab.nlp.Stopwords;
import org.ailab.nlp.annotation.IAnnotation;
import org.ailab.nlp.parsing.TokenAnnotation;
import org.ailab.nlp.parsing.chinese.ChineseParser;
import org.ailab.tem.DBConfig;
import org.ailab.wimfra.database.DBUtilInstance;
import org.ailab.wimfra.util.CountTable;
import org.ailab.wimfra.util.Int;
import org.ailab.wimfra.util.StringUtil;

import java.util.*;
import java.util.logging.Logger;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午3:33
 * Desc:
 */
public class IDFDictFactory {
    static Logger logger = Logger.getLogger(IDFDictFactory.class.getName());
    public static IDFDict wikiIDFDict;

    static {
        wikiIDFDict = IDFDictFactory.createFromDB(
                            "IDFDict from WikiAbst",
                            DBConfig.defaultDB,
                            "entity",
                            "abst",
                            null
                    );
    }

    public static IDFDict createFromDB(String name, DBConfig dbConfig, String tableName, String fieldName, String condition) {
        logger.info("create IDFDict from db");

        DBUtilInstance dbi = new DBUtilInstance(dbConfig.getName());
        String sql = "select " + fieldName + " from " + tableName;
        if (StringUtil.notEmpty(condition)) {
            sql += " where " + condition;
        }
        List<String> docList = dbi.getStringList(sql);
        logger.info(docList.size()+" documents loaded");

        IDFDict idfDict = createFromContentList(docList);
        return idfDict;
    }

    public static IDFDict createFromContentList(List contentList) {
        // 含有某个词的文档数计数器
        int docCount = contentList.size();
        CountTable wordDocCounter = new CountTable();
        for (int i = 0; i < contentList.size(); i++) {
            // todo
            if(i>100000) break;
            if(i%10000==0) {
                logger.info("segment document into words: "+(i+1)+"/"+contentList.size());
            }
            Object content = contentList.get(i);
            String doc;
            if(content == null) {
                doc = null;
            } else if(content instanceof IContent) {
                doc = ((IContent) content).getContent();
            } else {
                doc = content.toString();
            }
            // word segmentation
            List<TokenAnnotation> tokenAnnotationList = ChineseParser.parse(doc);
            Set<String> processedWordSet = new HashSet<String>();
            for (IAnnotation a : tokenAnnotationList) {
                String word = a.getText();
                if (Stopwords.isStopword(word)) {
                    continue;
                }
                if (processedWordSet.contains(word)) {
                    // continue;
                } else {
                    wordDocCounter.add(word);
                    processedWordSet.add(word);
                }
            }
        }

        // 对每个词，计算IDF，表示该词的普遍性
        Map<String, Double> wordToIDFMap = new HashMap<String, Double>(wordDocCounter.size());
        for (Map.Entry<Object, Int> entry : wordDocCounter.entrySet()) {
            String word = (String) entry.getKey();
            int wordDocCount = entry.getValue().i;
            double idf = getIDF(docCount, wordDocCount);
            wordToIDFMap.put(word, idf);
        }

        return new IDFDict(wordToIDFMap, docCount);
    }

    public static double getIDF(int docCount, int wordDocCount) {
        return Math.log((double) docCount / (wordDocCount + 1));
    }
}
