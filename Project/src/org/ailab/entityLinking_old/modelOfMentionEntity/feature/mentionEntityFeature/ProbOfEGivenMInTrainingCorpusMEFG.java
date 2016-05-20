package org.ailab.entityLinking_old.modelOfMentionEntity.feature.mentionEntityFeature;

import org.ailab.entityLinking_old.wim.doc.Doc;
import org.ailab.entityLinking_old.wim.docEntity.Mention;
import org.ailab.entityLinking_old.wim.entity.Entity;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午9:37
 * Desc:
 */
public class ProbOfEGivenMInTrainingCorpusMEFG extends MentionEntityFeatureGenerator {
    @Override
    public double generate(Doc doc, Mention mention, Entity entity) {
//        int entityOccurCount = trainingCorpusContext.getEntityOccurCount(entity.getId());
        int entityIdAndMentionCount = trainingCorpusContext.getEntityIdAndMentionCount(entity.getId(), mention.getMention());
        int mentionOccurCount = trainingCorpusContext.getMentionCount(mention.getMention());
        return (double) entityIdAndMentionCount
                / (mentionOccurCount+0.1);
    }

    @Override
    public String getFeatureShortName() {
        return "P(e|m)_TC";
    }
}
