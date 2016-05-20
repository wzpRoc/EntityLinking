package org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature;

import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.nlp.tfidf.IDFDict;
import org.ailab.nlp.tfidf.IDFDictFactory;
import org.ailab.nlp.tfidf.TFIDFProcessor;
import org.ailab.nlp.tfidf.TFProcessor;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc:
 */
public class SimilarityByTrainingCorpusMEFG extends SimilarityMEFG {
    @Override
    protected String getEntityContext(Entity entity) {
        return trainingCorpusContext.getEntityIdToContextMap().get(entity.getId());
    }


    @Override
    protected void initTFIDFProcessor() {
        IDFDict idfDict = IDFDictFactory.createFromContentList(trainingCorpusContext.getDocInfoList());
        this.tfidfProcessor = new TFIDFProcessor(idfDict, TFProcessor.getInstance());
    }


    @Override
    public String getFeatureShortName() {
        return "Sim(d,TC(e))";
    }

}
