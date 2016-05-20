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
public class RedirectMap {
    private static Map<Integer, String> srcIdToDestTitleMap;

    static {
        try {
            srcIdToDestTitleMap = DBUtil.getIntToStringMap("select rd_from, CONVERT(rd_title USING utf8) from wzp.wiki.redirect where rd_namespace=0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getDestPageTitle(int srcPageId) {
        return srcIdToDestTitleMap.get(srcPageId);
    }

    public static int getDestPageId(int srcPageId) {
        String destTitle = srcIdToDestTitleMap.get(srcPageId);
        if(destTitle == null) {
            return 0;
        } else {
            return WikiTitleAndPageIdMap.getPageId(destTitle);
        }
    }
}
