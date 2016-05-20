package org.ailab.entityLinking_old.modelOfMentionEntity.activeLearning;

import org.ailab.entityLinking_old.model.DocLinkResult;
import org.ailab.entityLinking_old.model.DocLinkResultByMention;
import org.ailab.wimfra.util.Comparer;
import org.ailab.wimfra.util.SortUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-19
 * Time: 上午1:42
 * Desc: 主动学习采样器，优先采样最大概率最小的实例，如：
 * 0.4 0.3 0.3
 * 0.5 0.5
 * 0.6 0.4
 */
public class MinMaxProbabilitySampler extends ALSampler {
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

    @Test
    public void test() {
        Comparer comparer = this.getDocLinkResultComparer();

        List<DocLinkResult> list = new ArrayList<DocLinkResult>();

        for(int i=0; i<3; i++) {
            DocLinkResult r1 = new DocLinkResultByMention(null);
            r1.probability0 = i/10.0;
            list.add(r1);
        }

        // 降序
        // 不确定 -> 确定
        // 小概率 -> 大概率
        List<DocLinkResult>  desList = SortUtil.sort(list, comparer, false);
        for(DocLinkResult result : desList) {
            System.out.println(result.probability0);
        }

        System.out.println();
    }
}
