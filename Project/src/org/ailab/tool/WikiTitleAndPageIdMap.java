package org.ailab.tool;

import org.ailab.wimfra.database.DBUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午1:57
 * Desc: 将维基标题和页面ID互相转化
 */
public class WikiTitleAndPageIdMap {
    private static Map<String, Integer> titleToIdMap;
    private static Map<Integer, String> idToTitleMap;

    static {
        try {
            titleToIdMap = DBUtil.getStringToIntMap("select CONVERT(page_title USING utf8), page_id from wiki.page p where p.page_namespace=0");
            
            idToTitleMap = new HashMap<Integer, String>(titleToIdMap.size());
            for(Map.Entry<String, Integer> entry : titleToIdMap.entrySet()) {
                idToTitleMap.put(entry.getValue(), entry.getKey());    
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
    }
    
    public static int getPageId(String wikiTitle) {
        Integer integer = titleToIdMap.get(wikiTitle);
        if(integer == null) {
            return 0;
        } else {
            return integer;
        }
    }

    /**
     * 如果当前页面是重定向页，那么返回重定向的目标页ID
     * @param wikiTitle
     * @return
     */
    public static int getPageIdOrRedirect(String wikiTitle) {
        Integer pageId = titleToIdMap.get(wikiTitle);
        if(pageId == null) {
            return 0;
        } else {
            int redirectDestPageId = RedirectMap.getDestPageId(pageId);
            if(redirectDestPageId == 0) {
                return pageId;
            } else {
                return redirectDestPageId;
            }
        }
    }

    public static String getWikiTitle(int pageId) {
        return idToTitleMap.get(pageId);
    }
}
