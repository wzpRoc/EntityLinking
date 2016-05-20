package org.ailab.entityLinking.wim.entityToEntityLink;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: lutingming
 * Date: 16-1-2
 * Time: 下午10:03
 * Desc: 入链缓存
 */
public class InlinkCache {
    private static Map<Integer, Set<Integer>> entityIdToInlinksMap = new HashMap<Integer, Set<Integer>>();
    private static Set<Integer> entityIdSetWithoutInlinks = new HashSet<Integer>();
    private static EntityToEntityLinkBL entityToEntityLinkBL = new EntityToEntityLinkBL();
    private static int totalInlinks = 0;

    public static Set<Integer> getInlinkSet(int toEntityId) {
        Set<Integer> inlinkSet = entityIdToInlinksMap.get(toEntityId);
        if(inlinkSet == null) {
            if(entityIdSetWithoutInlinks.contains(toEntityId)) {
                // 确实没有
            } else {
                // 麻烦您到数据库里查一下
                inlinkSet = entityToEntityLinkBL.getInlinkSet(toEntityId);
                if(inlinkSet==null || inlinkSet.size()==0) {
                    // 经查确实没有
                    entityIdSetWithoutInlinks.add(toEntityId);
                } else {
                    // 从数据库中查到了
                    entityIdToInlinksMap.put(toEntityId, inlinkSet);
                    totalInlinks+=inlinkSet.size();
                }
            }
        } else {
            //
        }
        return inlinkSet;
    }


    public static int getTotalInlinks() {
        return totalInlinks;
    }

}
