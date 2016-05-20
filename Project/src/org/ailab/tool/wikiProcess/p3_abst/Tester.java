package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiText;
import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiTextDAO;
import org.ailab.tool.wikiProcess.p2_plainText.PlainTextExtractorTester;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;

import java.util.List;

/**
 * Desc: 维基百科页面中文本抽取器
 */
public class Tester {
    public static void main(String[] args) throws Exception {
        // PlainTextExtractorTester.main(null);
        test();
    }

    private static void test() throws Exception {
        PageWikiTextDAO pageWikiTextDAO = new PageWikiTextDAO();
        pageWikiTextDAO.loadPlainText = true;
        pageWikiTextDAO.setTableName("test.pageWikiText");
        List<PageWikiText> list = pageWikiTextDAO.getList();
        for (int i = 0; i < list.size(); i++) {
            PageWikiText pageWikiText;
            pageWikiText = list.get(i);

//            pageWikiText.abst = WikiAbstExtractor.getAbst(pageWikiText.getPlainText());
            pageWikiText.abst = WikiAbstExtractor.getLongAbst(pageWikiText.getPlainText());

            FileUtil.write(pageWikiText.abst, "d:\\WikiAbst\\long_WikiAbst\\" + pageWikiText.title + ".txt");
        }

        DBUtil.batchUpdate("test.pageWikiText", list, "title", "abst");
    }


}