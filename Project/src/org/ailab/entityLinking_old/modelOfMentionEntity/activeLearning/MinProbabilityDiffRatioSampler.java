package org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning;

import org.ailab.entityLinking_old.model.DocLinkResult;
import org.ailab.wimfra.util.Comparer;

/**
 * User: Lu Tingming
 * Date: 15-5-19
 * Time: 上午1:42
 * Desc: 主动学习采样器，优先采样 (最大概率-次大概率)/(最大概率+次大概率) 最小的实例，如：
 * 0.5 0.5 result=0.0/1.0=0.0
 * 0.6 0.4 result=0.2/1.0=0.5
 * 0.4 0.0 result=0.4/0.4=1.0
 */
public class MinProbabilityDiffRatioSampler extends ALSampler {
    protected Comparer getDocLinkResultComparer() {
        return new Comparer() {
            @Override
            public int compare(Object o0, Object o1) {
                DocLinkResult result0 = (DocLinkResult) o0;
                DocLinkResult result1 = (DocLinkResult) o1;

                double diff = (result0.probability0 - result1.probability0)
                        /
                        (result0.probability0 + result1.probability0);
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
