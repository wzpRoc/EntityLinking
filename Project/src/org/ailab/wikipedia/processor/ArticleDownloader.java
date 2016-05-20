package org.ailab.wikipedia.processor;

import org.ailab.wikipedia.Util;
import org.ailab.wikipedia.crawler.Crawler;
import org.ailab.wikipedia.crawler.DownloadMessage;
import org.ailab.wikipedia.wim.article.Article;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-28
 * Time: 下午2:13
 */
public class ArticleDownloader {
    private Logger logger = Logger.getLogger(this.getClass());
    private String downloadBaseDir;
    protected Crawler downloader;
    protected boolean needDownloadArticle = true;
    protected boolean needPrintArticle = true;


    public ArticleDownloader(String downloadBaseDir) {
        this.downloadBaseDir = downloadBaseDir;
        this.downloader = new Crawler();
    }


    public List<Article> download(List<String> articleLinkList) throws Exception {
        List<Article> articleList = new ArrayList<Article>(articleLinkList.size());
        for (int i = 0; i < articleLinkList.size(); i++) {
            logger.debug("downloading article " + (i + 1) + "/" + articleLinkList.size());
            String articleLink = articleLinkList.get(i);
            final Article article;
            try {
                article = download(articleLink.replace(" ", "_"));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            if (article != null) {
                articleList.add(article);
                if (needPrintArticle) {
                    System.out.println((i + 1) + "/" + articleLinkList.size() + "\t" + article.getTitle());
                }
            }
        }

        return articleList;
    }


    public Article download(String articleLink) throws Exception {
        String fileName = StringUtil.extractFromLast(articleLink, "/");
        final String title = URLDecoder.decode(fileName);
        if ("寫給西藏的歌".equals(title)) {
            return null;
        }
        if (needDownloadArticle) {
            String articlePath = Util.getArticlePath(downloadBaseDir, URLDecoder.decode(articleLink));
            DownloadMessage downloadMessage = downloader.download(articleLink, articlePath);
            if (!downloadMessage.canProceed()) {
//                throw new Exception("下载文章页失败");
                logger.error("下载文章页失败");
                return null;
            }
        }

        Article article = new Article();
        article.setTitle(title);
        article.setLink(URLDecoder.decode(articleLink));

        return article;
    }


    public Crawler getDownloader() {
        return downloader;
    }

    public void setDownloader(Crawler downloader) {
        this.downloader = downloader;
    }


    public static void main(String[] args) {

    }
}
