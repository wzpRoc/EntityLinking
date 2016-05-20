package org.ailab.entityLinking_old.modelOfDocGlobal.feature.docGlobalFeature;

import org.ailab.entityLinking_old.modelOfDocGlobal.CandidateEntityPermutation;
import org.ailab.entityLinking_old.modelOfDocGlobal.feature.entityEntityFeature.EntityEntityFeatureGenerator;
import org.ailab.entityLinking_old.experiment.TrainingCorpusContext;
import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-29
 * Time: 下午10:47
 * Desc: 一篇文档中所有"实体-实体"组合的评分的平均值
 */
public class PermutationAvgScoreDFG extends DocFeatureGenerator {
    protected EntityEntityFeatureGenerator entityEntityFeatureGenerator;

    public PermutationAvgScoreDFG(EntityEntityFeatureGenerator entityEntityFeatureGenerator) {
        this.entityEntityFeatureGenerator = entityEntityFeatureGenerator;
    }

    @Override
    public double generate(Doc doc, List<DocEntity> mentionList, CandidateEntityPermutation candidateEntityPermutation) {
        if(mentionList.size()==1) {
            return 0;
        }

        int entityPairCount = 0;
        double totalScore = 0;

        // entity0有candidateEntityPermutation.size()-1种情况
        for (int idx0 = 0; idx0 < candidateEntityPermutation.size() - 1; idx0++) {
            // 固定entity0
            Entity entity0 = candidateEntityPermutation.get(idx0);
            // 对entity1循环
            for (int idx1 = idx0 + 1; idx1 < candidateEntityPermutation.size(); idx1++) {
                Entity entity1 = candidateEntityPermutation.get(idx1);
                if(entity0.getId() == entity1.getId()) {
                    continue;
                }
                double score = entityEntityFeatureGenerator.generate(entity0, entity1);
                totalScore += score;
                entityPairCount++;
            }
        }

        if(entityPairCount == 0) {
            return 0;
        }

        return totalScore / entityPairCount;
    }


    public void setTrainingCorpusContext(TrainingCorpusContext trainingCorpusContext) {
        super.setTrainingCorpusContext(trainingCorpusContext);
        entityEntityFeatureGenerator.setTrainingCorpusContext(trainingCorpusContext);
    }

    public String getFeatureShortName() {
        return "EE_AVG(" + entityEntityFeatureGenerator.getFeatureShortName()+")";
    }
}
