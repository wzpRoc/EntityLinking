package org.ailab.entityLinking.wim.entityToEntityLink;

import org.ailab.wimfra.database.DBUtil;

import java.sql.SQLException;
import java.util.Map;

/**
 * User: lutingming
 * Date: 16-1-2
 * Time: 下午10:03
 * Desc: 实体入链数缓存
 */
public class EntityInlinkCountCache {
    static Map<Integer, Integer> entityIdToInlinkCountMap;

    static {
        try {
            String sql = "select id, inlinkCount from entity";
            entityIdToInlinkCountMap = DBUtil.getIntToIntMap(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getInlinkCount(int entityId) {
        Integer count = entityIdToInlinkCountMap.get(entityId);
        if(count == null) {
            return 0;
        } else {
            return count;
        }
    }
}
