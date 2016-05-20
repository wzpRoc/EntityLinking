package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.entityLinking.wim.entity.EntityIdTitleMap;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.Stopwatch;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-13
 * Time: 下午3:53
 */
public class PageAbstUpdater {
    static Logger logger = Logger.getLogger(PageAbstUpdater.class);
    static PageAbstDAO pageAbstDAO = new PageAbstDAO();

    public static void main(String[] args) throws Exception {
        int numInOneBatch = 100000;
        int max = 48454407;

        int batchIdx = 19;
        while (true) {
            int start = batchIdx * numInOneBatch;
            int end = (batchIdx + 1) * numInOneBatch - 1;

            Stopwatch sw = new Stopwatch();

            System.out.println("processing batch " + batchIdx + ": " + start + " to " + end);
            List<PageAbst> pageAbstList = getPageAbstList(start, end);
            processPageAbstList(pageAbstList);

            System.out.println("finished batch " + batchIdx + ": " + start + " to " + end + "\t" + sw.stopAndGetFormattedTime());

            if (end >= max) {
                break;
            }
            batchIdx++;
        }
    }


    private static List<PageAbst> getPageAbstList(int start, int end) throws Exception {
        return pageAbstDAO.getListByCondition("id between " + start + " and " + end);
    }

    private static void processPageAbstList(List<PageAbst> pageAbstList) throws Exception {
        Map<Integer, String> entityIdToAbstMap = new HashMap<Integer, String>(pageAbstList.size());
        for (PageAbst pageAbst : pageAbstList) {
            int entityId = EntityIdTitleMap.getEntityId(pageAbst.title);
            if(entityId == 0) {
                logger.warn("can not find id for ["+pageAbst.id+","+pageAbst.title+"]");
                entityIdToAbstMap.put(pageAbst.id, pageAbst.abst);
            } else {
                entityIdToAbstMap.put(entityId, pageAbst.abst);
            }
        }

        DBUtil.batchUpdateByKeyValueMap(
                "entity",
                "id",
                "abst",
                entityIdToAbstMap);
    }
}
