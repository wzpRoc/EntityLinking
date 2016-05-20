package org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature;

import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.ml.WordVector;
import org.ailab.nlp.tfidf.TFIDFProcessor;
import org.apache.log4j.Logger;


import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc:
 */
public abstract class SimilarityMEFG extends MentionEntityFeatureGenerator {
    protected final Logger logger = Logger.getLogger(this.getClass());
    protected TFIDFProcessor tfidfProcessor;

    @Override
    public double generate(Doc doc, Mention mention, Entity entity) {
        if(tfidfProcessor == null) {
            initTFIDFProcessor();
        }

        WordVector vectorByMention = tfidfProcessor.getWordTFIDFVector(doc.getContent());
        WordVector vectorByEntity = tfidfProcessor.getWordTFIDFVector(getEntityContext(entity));

        double similarity = calcSimilarity(vectorByMention, vectorByEntity);
        return similarity;
    }

    protected double calcSimilarity(WordVector v1, WordVector v2) {
        if(v1.size() == 0 || v2.size() == 0) {
            return 0;
        }
        return multiply(v1, v2) / (Math.sqrt(multiply(v1, v1)*multiply(v2, v2)));
    }

    protected double multiply(WordVector v1, WordVector v2) {
        double sum = 0;
        for(Map.Entry<String, Double> entry : v1.entrySet()) {
            String word = entry.getKey();
            double value1 = entry.getValue();
            Double value2 = v2.get(word);
            if(value2 == null) {
                // continue;
            } else {
                sum += value1*value2;
            }
        }

        return sum;
    }


    protected abstract String getEntityContext(Entity entity);

    protected abstract void initTFIDFProcessor();
}
