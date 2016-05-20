package org.ailab.wikipedia.processor;

import org.ailab.common.config.ConfigBL;
import org.ailab.wikipedia.wim.article.Article;
import org.ailab.wikipedia.wim.article.ArticleBL;
import org.ailab.wikipedia.wim.category.Category;
import org.ailab.wikipedia.wim.category.CategoryBL;
import org.ailab.wimfra.database.DBUtil;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-28
 * Time: 下午2:13
 * 输入：category表
 * 输出：article表（未抽取详细信息）
 */
public class CrawlerController {
    Logger logger = Logger.getLogger(this.getClass());

    public List<Article> execute() throws Exception {
        String downloadBaseDir = ConfigBL.getValue("downloadBaseDir");
//        List<Category> articleList = (new CategoryBL()).getList();
        List<Category> categoryList = (new CategoryBL()).getListByCondition("id<>1989");
        List<Article> articleList = new ArrayList<Article>();

        for(int i=0; i<categoryList.size(); i++) {
            logger.info("process category " + (i + 1) + "/" + categoryList.size());
            String wikiCategoryLink = categoryList.get(i).getLink();
            CategoryToArticleCrawler crawler = new CategoryToArticleCrawler(downloadBaseDir, wikiCategoryLink);
//            crawler.setNeedDownloadArticle(false);
            final List<Article> articleListInCategory = crawler.craw();
            articleList.addAll(articleListInCategory);
        }

        List<String> articleLinkList = DBUtil.getStringList("select link from articleLink where state>=0");
        final List<Article> articleList1 = (new ArticleDownloader(downloadBaseDir)).download(articleLinkList);

        articleList.addAll(articleList1);

        return articleList;
    }


    public static void main(String[] args) throws Exception {
        final int crawTimes = ConfigBL.getIntValue("crawTimes", 10);
        List<Article> articleList = null;
        for(int i=1; i<= crawTimes; i++) {
            CrawlerController crawlerController = new CrawlerController();
            try {
                articleList = crawlerController.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(i<crawTimes-1) {
                Thread.sleep(1000*60*60);
            }
        }


        final ArticleBL articleBL = new ArticleBL();
        articleBL.backup(true);
        articleBL.truncate();
        articleBL.insert(articleList);
    }
}
