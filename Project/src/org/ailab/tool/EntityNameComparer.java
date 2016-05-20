package org.ailab.tool;

import org.ailab.entityLinking.wim.entityName.EntityName;
import org.ailab.wimfra.util.Comparer;

/**
 * User: lutingming
 * Date: 15-12-27
 * Time: 下午5:09
 */
public class EntityNameComparer implements Comparer {
    @Override
    public int compare(Object o0, Object o1) {
        return compare((EntityName)o0, (EntityName)o1);
    }


    public int compare(EntityName entityName0, EntityName entityName1) {
        double diff = entityName0.getProbOfNameToEntity() - entityName1.getProbOfNameToEntity();
        if(diff < 0) {
            return -1;
        } else if (diff == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
