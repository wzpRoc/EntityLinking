package org.ailab.wikipedia.processor;

import org.ailab.common.config.ConfigBL;
import org.ailab.wikipedia.Util;
import org.ailab.wikipedia.wim.article.Article;
import org.ailab.wikipedia.wim.article.ArticleBL;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-30
 * Time: 下午6:45
 */
public class ArticleExtractorController {
    protected String downloadBaseDir;
    protected ArticleBL articleBL;
    protected List<Article> articleList;

    public ArticleExtractorController() {
        downloadBaseDir = ConfigBL.getValue("downloadBaseDir");
        articleBL = new ArticleBL();
    }


    public void execute() throws Exception {
        getArticles();

        for(int i=0; i< articleList.size(); i++) {
            Article article = articleList.get(i);
            System.out.println("extract "+(i+1)+"/"+ articleList.size()+"\t"+article.getTitle());
            System.out.println(article.getLink());
            System.out.println(Util.getArticlePath(downloadBaseDir, article.getLink()));
            ArticleExtractor articleExtractor = createArticleExtractor(article);
            articleExtractor.setDownloadBaseDir(downloadBaseDir);
            articleExtractor.extract();
            postExtract(articleExtractor);
        }

        saveToDB();
    }

    protected void postExtract(ArticleExtractor articleExtractor) {

    }


    protected ArticleExtractor createArticleExtractor(Article article) {
        return new ArticleExtractor(article);
    }

    protected void getArticles() throws SQLException {
        articleList = articleBL.getListByCondition(getArticleCondition());
    }

    protected String getArticleCondition() {
        return null;
    }

    protected void saveToDB() throws Exception {
        articleBL.updateList(articleList);
    }

    public static void main(String[] args) throws Exception {
        ArticleExtractorController controller = new ArticleExtractorController();
        controller.execute();
    }

}
