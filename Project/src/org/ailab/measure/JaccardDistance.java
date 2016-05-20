package org.ailab.measure;

import org.ailab.wimfra.util.CollectionUtil;

import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-29
 * Time: 下午11:19
 * Desc:
 */
public class JaccardDistance {
    public static double calculate(Set set0, Set set1){
        int intersectionSize = CollectionUtil.getIntersectionSize(set0, set1);
        int unionSize = CollectionUtil.getUnionSize(set0, set1);

        if(unionSize == 0) {
            return 0;
        } else {
            return ((double)intersectionSize)
                    / unionSize;
        }
    }
}
