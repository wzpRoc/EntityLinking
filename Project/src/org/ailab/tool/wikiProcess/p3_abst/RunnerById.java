package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.entityLinking.wim.entity.EntityIdTitleMap;
import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiText;
import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiTextDAO;
import org.ailab.tool.wikiProcess.p2_plainText.PlainTextExtractor;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.Stopwatch;

import java.util.List;

/**
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午1:41
 * Desc: 读取pageWikiText，抽取文本，再抽取摘要，保存到wiki.pageAbst
 */
public class RunnerById {

    private static final PageWikiTextDAO pageWikiTextDAO = new PageWikiTextDAO();

    public static void main(String[] args) throws Exception {
//        DBUtil.truncate("wiki.pageAbst");

        int numInOneBatch = 100000;
        int max = 48454407;

        int batchIdx = 1;
        while (true) {
            int start = batchIdx * numInOneBatch;
            int end = (batchIdx + 1) * numInOneBatch - 1;

            Stopwatch sw = new Stopwatch();

            System.out.println("processing batch " + batchIdx + ": " + start + " to " + end);
            List<PageWikiText> pageWikiTextList = getPageWikiTextList(start, end);
            processWikiTextList(pageWikiTextList);

            System.out.println("finished batch " + batchIdx + ": " + start + " to " + end + "\t" + sw.stopAndGetFormattedTime());

            if (end >= max) {
                break;
            }
            batchIdx++;
        }
    }

    private static List<PageWikiText> getPageWikiTextList(int start, int end) throws Exception {
        return pageWikiTextDAO.getListByCondition("id between " + start + " and " + end);
    }

    private static void processWikiTextList(List<PageWikiText> pageWikiTextList) throws Exception {
        for (PageWikiText pageWikiText : pageWikiTextList) {
            String plainText = PlainTextExtractor.getPlainText(pageWikiText.wikiText);
//            pageWikiText.abst = WikiAbstExtractor.getAbst(plainText);
//            if(pageWikiText.abst!=null && pageWikiText.abst.length()>2000) {
//                pageWikiText.abst = pageWikiText.abst.substring(0, 2000);
//            }
            // 获得长摘要
            pageWikiText.abst = WikiAbstExtractor.getLongAbst(plainText);
            if (pageWikiText.abst != null && pageWikiText.abst.length() > 20000) {
                pageWikiText.abst = pageWikiText.abst.substring(0, 20000);
            }
            pageWikiText.title = pageWikiText.title.replace(' ', '_');
            // 映射entityId
            int entityId = EntityIdTitleMap.getEntityId(pageWikiText.title);
            pageWikiText.setEntityId(entityId);
        }

        DBUtil.batchInsert(
                "wiki.pageAbst",
                pageWikiTextList,
                "id",
                "entityId",
                "title",
                "abst");
    }
}
