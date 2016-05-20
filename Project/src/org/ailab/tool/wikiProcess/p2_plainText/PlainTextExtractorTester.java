package org.ailab.tool.wikiProcess.p2_plainText;

import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiText;
import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiTextDAO;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;

import java.util.List;

/**
 * Desc: 维基百科页面中文本抽取器
 */
public class PlainTextExtractorTester {
    public static void main(String[] args) throws Exception {
        test();
    }

    private static void test() throws Exception {
        PageWikiTextDAO pageWikiTextDAO = new PageWikiTextDAO();
        pageWikiTextDAO.setTableName("test.pageWikiText");
        List<PageWikiText> list = pageWikiTextDAO.getList();
        for (int i = 0; i < list.size(); i++) {
            PageWikiText pageWikiText;
            pageWikiText = list.get(i);

            pageWikiText.plainText = PlainTextExtractor.getPlainText(pageWikiText.wikiText);

            FileUtil.write(pageWikiText.plainText, "d:\\WikiText\\WikiText\\" + pageWikiText.title + ".txt");
        }

        DBUtil.batchUpdate("test.pageWikiText", list, "title", "plainText");
    }


}