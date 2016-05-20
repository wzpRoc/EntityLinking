package org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature;

import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.apache.log4j.Logger;


/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc: “提及-实体”特征生成器
 */
public abstract class MentionEntityFeatureGenerator implements IMentionEntityFeatureGenerator {
    protected final Logger logger = Logger.getLogger(this.getClass());
    // 特征生成是与训练数据相关的
    protected TrainingCorpusContext trainingCorpusContext;

    @Override
    public abstract double generate(Doc doc, Mention mention, Entity entity);

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


    public String toString() {
        return getFeatureShortName();
    }

}
