package org.ailab.entityLinking_old.cache;

import org.ailab.entityLinking_old.wim.wikiPageLink.WikiPageLinkDAO;
import org.apache.log4j.Logger;


import java.util.*;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午2:33
 * Desc:
 */
public class WikiInlinkCache {
    private static Logger logger = Logger.getLogger(WikiInlinkCache.class);
    private static Map<String, Set<String>> map;
    // 没有inlink的title的集合
    private static Set<String> setOfTitlesHasNoInlink;

    static {
        init();
    }

    private static WikiPageLinkDAO wikiPageLinkDAO;

    private static void init() {
        wikiPageLinkDAO = new WikiPageLinkDAO();
        map = new HashMap<String, Set<String>>();
        setOfTitlesHasNoInlink = new HashSet<String>();
    }

    public static Set<String> getInlinks(String title) {
        if(setOfTitlesHasNoInlink.contains(title)) {
            // yes, I know, this title has no inlink.
            // go away
            return null;
        } else {
            // query from cache
            Set<String> inlinks = map.get(title);
            if(inlinks == null) {
                // no found, then query from db
                inlinks = wikiPageLinkDAO.getInlinksByPageTitle(title);
                // empty -> null
                if(inlinks!=null && inlinks.size() == 1) {
                    inlinks = null;
                }
                if(inlinks == null) {
                    // null
                    setOfTitlesHasNoInlink.add(title);
                } else {
                    map.put(title, inlinks);
                }
            } else {
                // found in cache
            }
            return inlinks;
        }
    }
}
