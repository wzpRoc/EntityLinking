package org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature;

import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc: “提及-实体”特征生成器
 */
public interface IMentionEntityFeatureGenerator {
    public double generate(Doc doc, Mention mention, Entity entity);
    public void setTrainingCorpusContext(TrainingCorpusContext trainingCorpusContext);
    public String getFeatureName();
    public String getFeatureShortName();
}
