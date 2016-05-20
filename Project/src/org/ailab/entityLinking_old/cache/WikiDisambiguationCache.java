package org.ailab.entityLinking_old.cache;

import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.entityLinking_old.wim.entityName.EntityName;
import org.ailab.entityLinking_old.wim.entityName.EntityNameBL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午2:33
 * Desc:
 */
public class WikiDisambiguationCache extends HashMap<Integer, Entity> {
    private static Set<String> fromNameToTitleSet;

    static {
        init();
    }

/*
    private static void init() {
        WikiDisambiguationBL wikiDisambiguationBL = new WikiDisambiguationBL();
        List<WikiDisambiguation> list = wikiDisambiguationBL.getList();

        fromNameToTitleSet = new HashSet<String>(list.size());
        for(WikiDisambiguation disambiguation : list) {
            fromNameToTitleSet.add(disambiguation.getFromTitle()+"->"+disambiguation.getToTitle());
        }
    }
*/

    private static void init() {
        EntityNameBL entityNameBL = new EntityNameBL();
        List<EntityName> list = entityNameBL.getEntityNameListByPredicateName("wzp.wiki disambiguation");

        fromNameToTitleSet = new HashSet<String>(list.size());
        for(EntityName entityName : list) {
            fromNameToTitleSet.add(entityName.getEntityName() + "->" + entityName.getEntityTitle());
        }
    }

    public static boolean isWikiDisambiguation(String fromEntityName, String toEntityTitle) {
        return fromNameToTitleSet.contains(fromEntityName+"->"+toEntityTitle);
    }
}
