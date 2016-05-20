package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiText;
import org.ailab.tool.wikiProcess.p1_wikiText.PageWikiTextDAO;
import org.ailab.tool.wikiProcess.p0_file.WikiPageArticleXMLUtil;
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
public class RunnerByTitle {

    private static final PageWikiTextDAO pageWikiTextDAO = new PageWikiTextDAO();

    public static void main(String[] args) throws Exception {
        DBUtil.truncate("wiki.pageAbst");

        for (String fileTag : WikiPageArticleXMLUtil.fileTagList) {
            Stopwatch sw = new Stopwatch();

            System.out.println("processing batch " + fileTag);
            List<PageWikiText> pageWikiTextList = getPageWikiTextList(fileTag);
            processWikiTextList(pageWikiTextList);

            System.out.println("finished batch " + fileTag+ "\t" + sw.stopAndGetFormattedTime());
        }
    }

    private static List<PageWikiText> getPageWikiTextList(String fileTag) throws Exception {
        char startChar = fileTag.charAt(0);
        String condition;
        if(startChar>='A' && startChar<='Z') {
            condition =
                    "title like '"+startChar+"%'" +
                    " or title like '"+Character.toLowerCase(startChar)+"%'";
        } else if(startChar  == '0') {
            condition = "title>='0' and title<':'";
        } else {
            condition =
                    "title<'0' " +
                    "or (title>=':' and title<'A')" +
                    "or (title>='[' and title<'a')" +
                    "or title>='{'";
        }
        return pageWikiTextDAO.getListByCondition(condition);
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
