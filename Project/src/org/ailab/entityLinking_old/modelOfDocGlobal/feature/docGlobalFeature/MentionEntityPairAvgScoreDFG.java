package org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature;

import org.ailab.entityLinking_old.modelOfDocGlobal.CandidateEntityPermutation;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature.MentionEntityFeatureGenerator;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-29
 * Time: 下午10:47
 * Desc: 一篇文档中所有"提及-候选实体对"评分的平均值
 */
public class MentionEntityPairAvgScoreDFG extends DocFeatureGenerator {
    protected MentionEntityFeatureGenerator mentionEntityFeatureGenerator;

    public MentionEntityPairAvgScoreDFG(MentionEntityFeatureGenerator mentionEntityFeatureGenerator) {
        this.mentionEntityFeatureGenerator = mentionEntityFeatureGenerator;
    }

    @Override
    public double generate(Doc doc, List<DocEntity> mentionList, CandidateEntityPermutation candidateEntityPermutation) {
        double sum = 0;
        for(int i=0; i<mentionList.size(); i++) {
            Mention mention = mentionList.get(i);
            Entity entity = candidateEntityPermutation.get(i);
            double score = mentionEntityFeatureGenerator.generate(doc, mention, entity);
            sum += score;
        }
        return sum / mentionList.size();
    }


    public void setTrainingCorpusContext(TrainingCorpusContext trainingCorpusContext) {
        super.setTrainingCorpusContext(trainingCorpusContext);
        mentionEntityFeatureGenerator.setTrainingCorpusContext(trainingCorpusContext);
    }

    public String getFeatureShortName() {
        return "MEP_AVG(" + mentionEntityFeatureGenerator.getFeatureShortName()+")";
    }
}
