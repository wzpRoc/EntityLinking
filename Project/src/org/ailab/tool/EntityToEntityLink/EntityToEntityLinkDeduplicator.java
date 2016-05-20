package org.ailab.tool.EntityToEntityLink;

import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLink;
import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLinkBL;
import org.ailab.entityLinking.wim.pageLink.PageLink;
import org.ailab.tool.RedirectIdToIdMap;
import org.ailab.tool.WikiTitleAndPageIdMap;
import org.ailab.wimfra.util.Stopwatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午1:41
 * Desc: EntityToEntityLink表去重
 */
public class EntityToEntityLinkDeduplicator {
    public static void main(String[] args) throws Exception {
        int numInOneBatch = 10000;
        int max = 48454412;

        int batchIdx = 95;
        while (true) {
            int start = batchIdx * numInOneBatch;
            int end = (batchIdx + 1) * numInOneBatch - 1;

            Stopwatch sw = new Stopwatch();

            System.out.println("processing batch " + batchIdx + ": " + start + " to " + end);
            List<EntityToEntityLink> entityToEntityLinkList = getEELinkList(start, end);
            int writeCount = processEELinks(entityToEntityLinkList);

            System.out.println("finished batch " + batchIdx + ": " + start + " to " + end + "" +
                    "\tread=" + entityToEntityLinkList.size() +
                    "\twrite=" + writeCount +
                    "\t"+sw.stopAndGetFormattedTime());

            if (end >= max) {
                break;
            }
            batchIdx++;
        }
    }

    private static int processEELinks(List<EntityToEntityLink> entityToEntityLinks) throws Exception {
        List<EntityToEntityLink> resultList = new ArrayList<EntityToEntityLink>();

        HashSet<String> eeLinkSet = new HashSet<String>();;
        for (EntityToEntityLink eeLink : entityToEntityLinks) {
            // 检查是否重复
            String eeLinkKey = eeLink.getFromEntityId() + " " + eeLink.getToEntityId();
            if (eeLinkSet.contains(eeLinkKey)) {
                // 重复
            } else {
                eeLinkSet.add(eeLinkKey);
                // 生成一条实体-实体链接
                resultList.add(eeLink);
            }
        }

        (new EntityToEntityLinkBL()).batchInsert(resultList);

        return resultList.size();
    }


    private static List<EntityToEntityLink> getEELinkList(int start, int end) throws Exception {
        EntityToEntityLinkBL entityToEntityLinkBL = new EntityToEntityLinkBL();
        entityToEntityLinkBL.setTableName("entityToEntityLink_old");
        String condition = "toEntityId between " + start + " and " + end;
        return entityToEntityLinkBL.getListByCondition(condition);
    }
}
