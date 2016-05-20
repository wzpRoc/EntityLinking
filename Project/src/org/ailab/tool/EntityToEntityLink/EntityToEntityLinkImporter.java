package org.ailab.tool.EntityToEntityLink;

import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLink;
import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLinkBL;
import org.ailab.entityLinking.wim.pageLink.PageLink;
import org.ailab.tool.RedirectIdToIdMap;
import org.ailab.tool.RedirectMap;
import org.ailab.tool.WikiTitleAndPageIdMap;
import org.ailab.tool.aida.DatasetEntityIdSet;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.Stopwatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午1:41
 * Desc: 生成EntityToEntityLink表
 * 1. 读取pagelinks where pl_namespace=0 and pl_from_namespace=0
 * 2. 如果源节点或目标节点是重定向页，那么转换成重定向目标页
 * 3. 如果源节点或目标节点不在数据集中，那么跳过
 * 4. 将目标节点（pl_title）转换成id
 */
public class EntityToEntityLinkImporter {
    public static void main(String[] args) throws Exception {
//        (new EntityToEntityLinkBL()).truncate();

        int numInOneBatch = 10000;
        int max = 48454412;

        int batchIdx = 0;
        while (true) {
            int start = batchIdx * numInOneBatch;
            int end = (batchIdx + 1) * numInOneBatch - 1;

            Stopwatch sw = new Stopwatch();

            System.out.println("processing batch " + batchIdx + ": " + start + " to " + end);
            List<PageLink> pageLinkList = getPageLinks(start, end);
            processPageLinks(pageLinkList);

            System.out.println("finished batch " + batchIdx + ": " + start + " to " + end + "\t" + sw.stopAndGetFormattedTime());

            if (end >= max) {
                break;
            }
            batchIdx++;
        }
    }

    private static void processPageLinks(List<PageLink> pageLinkList) throws Exception {
        Set<String> eeLinkSet = new HashSet<String>();
        List<EntityToEntityLink> eeLinkList = new ArrayList<EntityToEntityLink>();

        for (PageLink pageLink : pageLinkList) {
            // 处理重定向
            {
                int redirectDestPageId = RedirectIdToIdMap.getDestPageId(pageLink.pl_from);
                if(redirectDestPageId>0) {
                    pageLink.pl_from = redirectDestPageId;
                } else {
//                    System.out.print(""); // 源节点不是重定向
                }
            }
            {
                pageLink.pl_to = WikiTitleAndPageIdMap.getPageId(pageLink.pl_title);
                if(pageLink.pl_to > 0) {
                    int redirectDestPageId = RedirectIdToIdMap.getDestPageId(pageLink.pl_to);
                    if(redirectDestPageId > 0) {
                        pageLink.pl_to = redirectDestPageId;
                    } else {
//                        System.out.print("");
                    }
                } else {
//                    System.out.print("");    // 目标节点不是重定向
                }
            }

            if(pageLink.pl_from == 0
                    || pageLink.pl_to == 0
                    ||pageLink.pl_from == pageLink.pl_to) {
                continue;
            }

            // 检查是否重复
            String eeLinkKey = pageLink.pl_from + " " + pageLink.pl_title;
            if (eeLinkSet.contains(eeLinkKey)) {
                // 重复
                System.out.print("");
            } else {
                eeLinkSet.add(eeLinkKey);
                // 生成一条实体-实体链接
                EntityToEntityLink eeLink = new EntityToEntityLink();
                eeLink.setFromEntityId(pageLink.pl_from);
                eeLink.setToEntityId(pageLink.pl_to);
                eeLinkList.add(eeLink);
            }
        }

        (new EntityToEntityLinkBL()).batchInsert(eeLinkList);
    }

    private static List<PageLink> getPageLinks(int start, int end) throws Exception {
        String sql =
                "select pl_from, pl_title " +
                        "\nfrom wzp.wiki.pagelinks pl" +
                        "\nwhere pl_from between " + start + " and " + end +
                        "\nand pl_namespace=0 " +
                        "\nand pl_from_namespace=0";
        return DBUtil.getObjectList(sql, PageLink.class);
    }
}
