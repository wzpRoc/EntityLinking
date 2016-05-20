package org.ailab.entityLinking_old.cache;

import org.ailab.entityLinking_old.wim.wikiFact.WikiFactDAO;
import org.apache.log4j.Logger;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午2:33
 * Desc:
 */
public class NLPCCCategoryCache {
    private static Logger logger = Logger.getLogger(NLPCCCategoryCache.class);
    private static Map<Integer, Set<String>> map;
    // 没有inlink的title的集合
    private static Set<Integer> setOfEntityIdsHasNoCategory;

    static {
        init();
    }

    private static WikiFactDAO wikiFactDAO;

    private static void init() {
        wikiFactDAO = new WikiFactDAO();
        map = new HashMap<Integer, Set<String>>();
        setOfEntityIdsHasNoCategory = new HashSet<Integer>();
    }

    public static Set<String> getCategories(int entityId) {
        if(setOfEntityIdsHasNoCategory.contains(entityId)) {
            // yes, I know, this title has no inlink.
            // go away
            return null;
        } else {
            // query from cache
            Set<String> inlinks = map.get(entityId);
            if(inlinks == null) {
                // no found, then query from db
                inlinks = wikiFactDAO.getCategoriesByEntityId(entityId);
                // empty -> null
                if(inlinks!=null && inlinks.size() == 1) {
                    inlinks = null;
                }
                if(inlinks == null) {
                    // null
                    setOfEntityIdsHasNoCategory.add(entityId);
                } else {
                    map.put(entityId, inlinks);
                }
            } else {
                // found in cache
            }
            return inlinks;
        }
    }
}
