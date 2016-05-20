package org.ailab.entityLinking.wim.entity;

import org.ailab.wimfra.database.DBUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午1:57
 * Desc: 将维基标题和实体ID互相转化
 */
public class EntityIdTitleMap {
    private static Map<String, Integer> titleToIdMap;
    private static Map<Integer, String> idToTitleMap;

    static {
        try {
            idToTitleMap = DBUtil.getIntToStringMap("select id, CONVERT(wikiTitle USING utf8) from entity");

            titleToIdMap = new HashMap<String, Integer>(idToTitleMap.size());
            for(Map.Entry<Integer, String> entry : idToTitleMap.entrySet()) {
                titleToIdMap.put(entry.getValue(), entry.getKey());
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }
    
    public static int getEntityId(String wikiTitle) {
        Integer integer = titleToIdMap.get(wikiTitle);
        if(integer == null) {
            return 0;
        } else {
            return integer;
        }
    }

    public static String getWikiTitle(int entityId) {
        return idToTitleMap.get(entityId);
    }
}
