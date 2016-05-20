package org.ailab.entityLinking_old.cache;

import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.entityLinking_old.wim.entityName.EntityName;
import org.ailab.entityLinking_old.wim.entityName.EntityNameBL;

import java.util.HashMap;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午2:33
 * Desc:
 */
public class WikiRedirectCache extends HashMap<Integer, Entity> {
    private static HashMap<String, String> nameToEntityTitleMap;

    static {
        init();
    }

/*
    private static void init() {
        WikiRedirectBL wikiRedirectBL = new WikiRedirectBL();
        nameToEntityTitleMap = wikiRedirectBL.getNameToEntityTitleMap();
    }
*/
private static void init() {
    EntityNameBL entityNameBL = new EntityNameBL();
    List<EntityName> list = entityNameBL.getEntityNameListByPredicateName("wzp.wiki redirect");

    nameToEntityTitleMap = new HashMap<String, String>(list.size());
    for(EntityName entityName : list) {
        nameToEntityTitleMap.put(entityName.getEntityName(), entityName.getEntityTitle());
    }
}


    public static boolean isWikiRedirect(String entityName, String entityTitle) {
        String titleFromMap = nameToEntityTitleMap.get(entityName);
        return titleFromMap!=null && titleFromMap.equals(entityTitle);
    }
}
