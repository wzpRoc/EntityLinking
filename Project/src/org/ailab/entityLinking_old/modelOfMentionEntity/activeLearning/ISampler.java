package org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning;

import org.ailab.entityLinking_old.model.LinkModelType;

import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-19
 * Time: 上午1:42
 * Desc:
 */
public interface ISampler {
    public Set<Integer> sample(LinkModelType linkModelType, String classificationModel, Object featureGenerators, Set<Integer> sampledTrainingDocIdSet, Set<Integer> unsampledTrainingDocIdSet, int sampleSize, String featureVectorsLogPath);

    public String getName();
}
