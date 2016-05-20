package org.ailab.entityLinking_old.cache;

import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.docEntity.DocEntityBL;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.wimfra.util.map.MultiValueMap;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午1:44
 * Desc:
 */
public class EntityNameCache {
    private static MultiValueMap instance;

    static {
        init();
    }

    private static void init() {
        instance = new MultiValueMap();

        DocEntityBL bl = new DocEntityBL();
        List<DocEntity> list = bl.getList();
        for(DocEntity de : list) {
            instance.add(de.getMention(), de);
        }
    }

    public static List<Entity> getEntityListByName(String name) {
        return (List<Entity>) instance.getList(name);
    }
}
