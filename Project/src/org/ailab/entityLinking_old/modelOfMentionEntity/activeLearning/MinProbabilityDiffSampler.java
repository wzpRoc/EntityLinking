package org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning;

import org.ailab.entityLinking_old.model.DocLinkResult;
import org.ailab.wimfra.util.Comparer;

/**
 * User: Lu Tingming
 * Date: 15-5-19
 * Time: 上午1:42
 * Desc: 主动学习采样器，优先采样最大概率和次大概率差值最小的实例，如：
 * 0.5 0.5 diff=0.0
 * 0.6 0.4 diff=0.2
 * 0.4 0.0 diff=0.4
 */
public class MinProbabilityDiffSampler extends ALSampler {
    protected Comparer getDocLinkResultComparer() {
        return new Comparer() {
            @Override
            public int compare(Object o0, Object o1) {
                DocLinkResult result0 = (DocLinkResult) o0;
                DocLinkResult result1 = (DocLinkResult) o1;

                double diff = result0.probability0 - result1.probability0;
                if(diff<0) {
                    return 1;
                } else if(diff ==0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        };
    }
}
