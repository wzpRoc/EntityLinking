package org.ailab.entityLinking_old.cache;

import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.entityLinking_old.wim.entity.EntityBL;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午2:33
 * Desc:
 */
public class EntityCache {
    private static Logger logger = Logger.getLogger(EntityCache.class);
    private static HashMap<Integer, Entity> idToEntityMap;
    private static HashMap<String, Entity> titleToEntityMap;

    static {
        init();
    }

    private static void init() {
        EntityBL entityBL = new EntityBL();
        // List<Entity> list = entityBL.getPLOList();
        @SuppressWarnings("unchecked")
        List<Entity> list = entityBL.getListByCondition("includeTag>=1");
        idToEntityMap = new HashMap<Integer, Entity>(list.size());
        titleToEntityMap = new HashMap<String, Entity>(list.size());
        for(Entity entity : list) {
            idToEntityMap.put(entity.getId(), entity);
            titleToEntityMap.put(entity.getTitle(), entity);
        }

        idToEntityMap.put(Entity.NIL.getId(), Entity.NIL);
        titleToEntityMap.put(Entity.NIL.getTitle(), Entity.NIL);
    }

    public static List<Entity> getList(Collection idCollection) {
        if(idCollection == null) return null;
        List<Entity> entityList = new ArrayList<Entity>(idCollection.size());
        for(Object keyObj : idCollection) {
            int id;
            if(keyObj instanceof Integer) {
                id = (Integer) keyObj;
            } else {
                id = Integer.parseInt((String) keyObj);
            }
            Entity e = idToEntityMap.get(id);
            if(e == null) {
                // logger.debug("entity not found in cache, id="+id);
            } else {
                entityList.add(e);
            }
        }

        return entityList;
    }

    public static Entity getById(int id) {
        return idToEntityMap.get(id);
    }

    public static Entity getByTitle(String title) {
        return titleToEntityMap.get(title);
    }

    public static int getIdByTitle(String title) {
        Entity entity = getByTitle(title);
        if(entity == null) {
            return Entity.NIL.getId();
        } else {
            return entity.getId();
        }
    }
}
