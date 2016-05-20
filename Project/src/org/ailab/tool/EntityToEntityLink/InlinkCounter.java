package org.ailab.tool.EntityToEntityLink;

import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLink;
import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLinkBL;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.BiInteger;
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
public class InlinkCounter {
    public static void main(String[] args) throws Exception {
        int numInOneBatch = 100000;
        int max = 48454412;

        int batchIdx = 382;
        while (true) {
            int start = batchIdx * numInOneBatch;
            int end = (batchIdx + 1) * numInOneBatch - 1;

            Stopwatch sw = new Stopwatch();

            System.out.println("processing batch " + batchIdx + ": " + start + " to " + end);
            List<BiInteger> inlinkCountList = getInlinkCountList(start, end);
            int writeCount = DBUtil.batchUpdateByBiIntegerList("update entity set inlinkCount=? where id=?", inlinkCountList);

            System.out.println("finished batch " + batchIdx + ": " + start + " to " + end + "" +
                    "\tread=" + inlinkCountList.size() +
                    "\twrite=" + writeCount +
                    "\t"+sw.stopAndGetFormattedTime());

            if (end >= max) {
                break;
            }
            batchIdx++;
        }
    }


    private static List<BiInteger> getInlinkCountList(int start, int end) throws Exception {
        return DBUtil.getBiIntegerList(
                "SELECT COUNT(*), toEntityId\n" +
                        "FROM   entityToEntityLink eel\n" +
                        "WHERE  toEntityId BETWEEN " + start + " AND "+end+"\n" +
                        "GROUP  BY toEntityId");
    }
}
