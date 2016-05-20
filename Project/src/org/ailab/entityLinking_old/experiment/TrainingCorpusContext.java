package org.ailab.entityLinking_old.experiment;

import org.ailab.entityLinking_old.util.DocInfo;
import org.ailab.entityLinking_old.cache.EntityCache;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;
import org.ailab.wimfra.util.CountTable;
import org.ailab.wimfra.util.Int;
import org.ailab.wimfra.util.map.MultiValueSetMap;

import java.util.*;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午7:50
 * Desc:
 */
public class TrainingCorpusContext extends CorpusContext {
    private CountTable entityOccurCounter;
    private CountTable mentionOccurCounter;
    private CountTable entityIdAndMentionCounter;
    private MultiValueSetMap mentionToEntityIdsMap;
    private int sumEntityCount;

    public TrainingCorpusContext(Set<Integer> docIdSet) {
        super(docIdSet);

        entityOccurCounter = new CountTable();
        mentionOccurCounter = new CountTable();
        entityIdAndMentionCounter = new CountTable();
        mentionToEntityIdsMap = new MultiValueSetMap();
        sumEntityCount = 0;

        for(DocInfo docInfo : docInfoList) {
            if(docInfo.docEntityList==null) {
                continue;
            }
            for(DocEntity docEntity : docInfo.docEntityList) {
                entityOccurCounter.add(docEntity.getEntityId());
                mentionOccurCounter.add(docEntity.getMention());
                entityIdAndMentionCounter.add(docEntity.getEntityId()+"->"+docEntity.getMention());
                mentionToEntityIdsMap.add(docEntity.getMention(), docEntity.getEntityId());
                sumEntityCount++;
            }
        }
    }

    public int getEntityOccurCount(int entityId) {
        Int occurCount = entityOccurCounter.get(entityId);
        if(occurCount == null) {
            return 0;
        }
        return occurCount.i;
    }

    public int getEntityIdAndMentionCount(int entityId, String mention) {
        Int occurCount = entityIdAndMentionCounter.get(entityId+"->"+mention);
        if(occurCount == null) {
            return 0;
        }
        return occurCount.i;
    }

    public int getMentionCount(String mention) {
        Int occurCount = mentionOccurCounter.get(mention);
        if(occurCount == null) {
            return 0;
        }
        return occurCount.i;
    }

    public int getSumEntityCount() {
        return sumEntityCount;
    }

    public List<Entity> getEntityList(String mention) {
        @SuppressWarnings("unchecked")
        Set<Integer> entityIds = mentionToEntityIdsMap.getSet(mention);
        return EntityCache.getList(entityIds);
    }
}
