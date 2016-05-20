package org.ailab.tool.aida;

import org.ailab.wimfra.database.DBUtil;

import java.sql.SQLException;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午1:51
 * 数据集实体集合
 */
public class DatasetEntityIdSet {
    private static HashSet<Integer> datasetEntityIdSet;
    static {
        try {
            datasetEntityIdSet = DBUtil.getIntHashSet("select distinct entityId from el.mention where entityId<>0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean contains(int entityId) {
        return datasetEntityIdSet.contains(entityId);
    }
}
