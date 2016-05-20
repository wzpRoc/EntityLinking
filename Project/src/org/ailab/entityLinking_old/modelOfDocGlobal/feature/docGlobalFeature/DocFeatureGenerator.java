package org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature;

import org.ailab.entityLinking_old.modelOfDocGlobal.CandidateEntityPermutation;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.apache.log4j.Logger;


import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc: “提及-实体”特征生成器
 */
public abstract class DocFeatureGenerator implements IDocFeatureGenerator {
    protected final Logger logger = Logger.getLogger(this.getClass());
    // 特征生成是与训练数据相关的
    protected TrainingCorpusContext trainingCorpusContext;

    @Override
    public abstract double generate(Doc doc, List<DocEntity> mentionList, CandidateEntityPermutation candidateEntityPermutation);


    public String getFeatureName() {
        return this.getClass().getName();
    }

    public String getFeatureShortName() {
        return this.getClass().getName();
    }

    public TrainingCorpusContext getTrainingCorpusContext() {
        return trainingCorpusContext;
    }

    public void setTrainingCorpusContext(TrainingCorpusContext trainingCorpusContext) {
        this.trainingCorpusContext = trainingCorpusContext;
    }

    @Override
    public String toString() {
        return getFeatureShortName();
    }
}
