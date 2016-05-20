package org.ailab.entityLinking_old.candidateEntityGenerate;

import org.ailab.entityLinking_old.cache.EntityCache;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.entityLinking_old.wim.entityName.EntityNameBL;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午1:44
 * Desc:
 */
public class EntityQuerier {
    private EntityNameBL entityNameBL = new EntityNameBL();

    public List<Entity> queryByName(String name) {
        List<Integer> entityIdList = entityNameBL.getEntityIdListByEntityName(name);
        // 从cache里面取实体，使得不用创建新对象，大大减少内存占用！
        return EntityCache.getList(entityIdList);
    }
}
