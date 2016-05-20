package org.ailab.tool.wikiProcess.p1_wikiText;

import org.ailab.tool.wikiProcess.p0_file.WikiPageArticleXMLUtil;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.Stopwatch;
import org.ailab.wimfra.util.StringUtil;
import wzp.wiki.PageCallbackHandler;
import wzp.wiki.WikiPage;
import wzp.wiki.WikiXMLParser;
import wzp.wiki.WikiXMLParserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-11-26
 * Time: 上午10:46
 * Desc: 从维基百科pages-articles抽取页面内容
 * 1. 只保留在entity表的记录（用id过滤）
 * 2. 保存的字段包括id, title, wikiText
 * 3. 每1000条保存一次
 */
public class PageWikiTextExtractor {
    static int acceptanceCount;
    static int rejectionCount;
    static Set<Integer> entityIdSet;
    static final int batchSize = 10000;

    public static void main(String args[]) throws Exception {
        acceptanceCount = 0;
        rejectionCount = 0;
//        entityIdSet = (new EntityBL()).getIdSet();

        for (String fileTag : WikiPageArticleXMLUtil.fileTagList) {
            if(fileTag.matches("[A-Z]")) {
                continue;
            }
            String filePath = "F:\\Wikipedia\\pages-articles\\"+fileTag+".xml";
            extractFile(filePath);
        }
//        extractFile("X");
    }

    public static void extractFile(String filePath) throws Exception {
        System.out.println("Processing file: "+filePath);
        WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(filePath);
        final List<PageWikiText> pageWikiTextList = new ArrayList<PageWikiText>(batchSize);
        final Stopwatch stopwatch = new Stopwatch();

        wxsp.setPageCallback(new PageCallbackHandler() {
            public void process(WikiPage page) {
                int id = Integer.parseInt(page.getID());
                if (true || entityIdSet.contains(id)) {
                    acceptanceCount++;

                    PageWikiText pageWikiText = new PageWikiText();
                    pageWikiText.id = id;
                    pageWikiText.title = page.getTitle();
//                    pageWikiText.wikiText = StringUtil.trim(page.getText());
                    pageWikiText.wikiText = StringUtil.trim(page.getWikiText());

                    pageWikiTextList.add(pageWikiText);

                    if (pageWikiTextList.size() == batchSize) {
                        save(pageWikiTextList);
                        System.out.println("----------------------- accept=" + acceptanceCount + ", reject=" + rejectionCount + ", " + stopwatch.stopAndGetFormattedTime());
                        pageWikiTextList.clear();
                        stopwatch.start();
                    }
                } else {
                    System.out.println("reject: "+page.getID()+", "+page.getTitle());
                    rejectionCount++;
                }
            }
        });

        wxsp.parse();

        if (pageWikiTextList.size() > 0) {
            save(pageWikiTextList);
            System.out.println("----------------------- accept=" + acceptanceCount + ", reject=" + rejectionCount + ", " + stopwatch.stopAndGetFormattedTime());
        }
    }


    private static void save(List<PageWikiText> pageWikiTextList) {
        try {
            DBUtil.batchInsert(
                    "wiki.pageWikiText",
                    pageWikiTextList
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
