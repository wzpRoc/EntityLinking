package org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature;

import org.ailab.entityLinking_old.modelOfDocGlobal.CandidateEntityPermutation;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc: 文档特征生成器
 */
public interface IDocFeatureGenerator {
    public double generate(Doc doc, List<DocEntity> mentionList, CandidateEntityPermutation candidateEntityPermutation);
    public void setTrainingCorpusContext(TrainingCorpusContext trainingCorpusContext);
    public String getFeatureName();
    public String getFeatureShortName();
}
