package org.ailab.tool;

import org.ailab.wimfra.database.DBUtil;

import java.sql.SQLException;
import java.util.Map;

/**
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午2:09
 * Desc: 重定向映射
 */
public class RedirectIdToIdMap {
    private static Map<Integer, Integer> srcIdToDestIdMap;

    static {
        try {
            srcIdToDestIdMap = DBUtil.getIntToIntMap(
                    "select rd_from, rd_page_id " +
                            "\nfrom wzp.wiki.redirect " +
                            "\nwhere rd_namespace=0" +
                            "\nand   rd_from!=0" +
                            "\nand   rd_page_id!=0" +
                            "\nand   rd_from!=rd_page_id" +
                            "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getDestPageId(int srcPageId) {
        Integer destId = srcIdToDestIdMap.get(srcPageId);
        if(destId == null) {
            return 0;
        } else {
            return destId;
        }
    }
}
