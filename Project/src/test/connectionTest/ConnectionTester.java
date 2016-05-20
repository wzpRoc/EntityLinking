package test.connectionTest;

import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiText;
import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiTextDAO;
import org.ailab.tool.wikiProcess.p2_plainText.PlainTextExtractor;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.tool.wikiProcess.p3_abst.WikiAbstExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lutingming
 * Date: 15-12-21
 * Time: 下午1:41
 * Desc: 读取pageWikiText，抽取文本，再抽取摘要，保存到wiki.pageAbst
 */
public class ConnectionTester {

    private static final PageWikiTextDAO pageWikiTextDAO = new PageWikiTextDAO();

    public static void main(String[] args) throws Exception {
        List<Test> testList = new ArrayList<Test>();
        testList.add(new Test());

        for(int i=1; i<=1000; i++) {
            System.out.println("processing batch " + i);
//            pageWikiTextDAO.getListByCondition("id="+i);                                      // ok
//            (new PageWikiTextDAO()).getListByCondition("id="+i);                              // ok
//            pageWikiTextDAO.executeUpdate("insert into test.test(i) values(?)", i);           // ok
//            (new PageWikiTextDAO()).executeUpdate("insert into test.test(i) values(?)", i);   // ok
//            DBUtil.getValueList("select * from test.test where i=?", i);                      // ok
//            DBUtil.batchInsert("test.test", testList);                                        // !!!!!! 279
            DBUtil.executeUpdate("insert into test.test(i) values(?)", i);                      // ok
        }
    }


    private static void processWikiTextList(List<PageWikiText> pageWikiTextList) throws Exception {
        for (PageWikiText pageWikiText : pageWikiTextList) {
            String plainText = PlainTextExtractor.getPlainText(pageWikiText.wikiText);
            pageWikiText.abst = WikiAbstExtractor.getAbst(plainText);
        }

        DBUtil.batchInsert(
                "wiki.pageAbst",
                pageWikiTextList,
                "id",
                "title",
                "abst");
    }
}
