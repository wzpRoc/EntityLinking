package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.entityLinking.wim.entity.Entity;
import org.ailab.entityLinking.wim.entity.EntityDAO;
import org.ailab.wimfra.util.cache.Cache;

/**
 * User: lutingming
 * Date: 16-1-16
 * Time: 下午2:22
 * Desc: 维基页面摘要缓存
 */
public class EntityCache extends Cache<Integer, String> {
    protected EntityDAO entityDAO;

    protected EntityCache(int maxSize) {
        super(maxSize);
        this.entityDAO = new EntityDAO();
    }

    @Override
    protected String query(Integer entityId) {
        Entity entity = entityDAO.get(entityId);
        if(entity == null) {
            return null;
        } else {
            return entity.getAbst();
        }
    }
}
