package org.ailab.wikipedia.processor;

import org.ailab.webInformationExtraction.CvNodeFilter;
import org.ailab.wikipedia.crawler.Crawler;
import org.ailab.wikipedia.crawler.DownloadMessage;
import org.ailab.webInformationExtraction.Parser;
import org.ailab.wikipedia.wim.article.Article;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;
import org.htmlparser.Node;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-28
 * Time: 下午2:13
 */
public class CategoryToArticleCrawler {
    private Logger logger = Logger.getLogger(this.getClass());
    private String downloadBaseDir;
    private String categoryLink;
    private String linkPrefix;
    protected Crawler downloader;
    protected boolean needDownloadArticle = true;
    protected boolean needPrintArticle = true;
    ArticleDownloader articleDownloader;

    public CategoryToArticleCrawler(String downloadBaseDir, String categoryLink) {
        this.downloadBaseDir = downloadBaseDir;
        this.categoryLink = categoryLink;
        this.downloader = new Crawler();
        this.downloader.sleepSecondsAfterDownload = 2;
        this.linkPrefix = StringUtil.extractBefore(categoryLink, "/wzp/wiki");

        articleDownloader = new ArticleDownloader(downloadBaseDir);
        articleDownloader.setDownloader(downloader);
    }


    /**
     * @return 指定分类下的文章本地地址列表
     */
    public List<Article> craw() throws Exception {
        final List<Article> articleList = new ArrayList<Article>();
        final List<String> categoryPagePathList = downloadCategoryPages();
        for(String categoryPagePath : categoryPagePathList) {
            final List<Article> articleListInPage = craw(categoryPagePath);
            if(articleListInPage!=null) {
                articleList.addAll(articleListInPage);
            }
        }

        return articleList;
    }


    /**
     * 返回类别页面路径列表
     */
    public List<String> downloadCategoryPages() throws Exception {
        List<String> categoryPathList = new ArrayList<String>();

        int pageNo = 1;
        String nextCategoryPageLink = categoryLink;

        while (true) {
            // 下载网页
            String nextCategoryPagePath = getCategoryPath(nextCategoryPageLink, pageNo);
            DownloadMessage downloadMessage;
            downloadMessage = downloader.download(nextCategoryPageLink, nextCategoryPagePath);
            if (!downloadMessage.canProceed()) {
                logger.error("下载列表页失败，pageNo=" + pageNo);
                break;
            }
            categoryPathList.add(nextCategoryPagePath);

            // 找下一页
            // 创建解析器
            Parser parser = new Parser();
            parser.setPath(nextCategoryPagePath);
            final Node pageContainerNode = parser.findOnlyNode(new CvNodeFilter("div", "id", "mw-pages"));
            // 下一页节点
            final Node nextPageNode = Parser.findFirstOffspringIn(pageContainerNode, (new CvNodeFilter("a")).setText("之后200次"));
            if (nextPageNode == null) {
                break;
            }
            // 下一页链接
            nextCategoryPageLink = linkPrefix + ((TagNode) nextPageNode).getAttribute("href").replace("&amp;", "&");
            pageNo++;
        }

        return categoryPathList;
    }


    /**
     * @return 指定分类下的文章列表
     */
    public List<Article> craw(String categoryPath) throws Exception {
        // 抽取文章链接
        List<String> articleLinkList = extract(categoryPath);
        return articleDownloader.download(articleLinkList);
    }


    /**
     * 从分类页面抽取出文章链接列表
     */
    public List<String> extract(String categoryPath) throws Exception {
        List<String> articleLinkList = new ArrayList<String>();

        Parser parser = new Parser();
        parser.setPath(categoryPath);
        final Node pageContainerNode = parser.findOnlyNode(new CvNodeFilter("div", "id", "mw-pages"));
        if (pageContainerNode != null) {
            final NodeList nodeList = parser.findOffspringIn(pageContainerNode, (new CvNodeFilter("A")).addAttributeRegex("href", "/wzp/wiki/.+"));
            for (Node node : nodeList.toNodeArray()) {
                LinkTag linkTag = (LinkTag) node;
                final String href = linkTag.getAttribute("href");
                if (href.contains("/Category:")
                        || href.contains("/User:")
                        || href.contains("/Special:")
                        || href.contains("/Wikipedia:")
                        || href.contains("/Portal:")
                        || href.contains("/Help:")
                        || href.contains("/Project:")
                        ) {
                    continue;
                }
                final String link = linkPrefix + href;
                articleLinkList.add(link);
            }
        }

        return articleLinkList;
    }


    /**
     */
    public String getCategoryPath(String categoryLink) {
        return getCategoryPath(categoryLink, 1);
    }


    /**
     */
    public String getCategoryPath(String categoryLink, int pageNo) {
        // http://zh.wikipedia.org/wzp.wiki/Category:%E9%A6%99%E6%B8%AF%E7%94%B7%E6%AD%8C%E6%89%8B
        String pageNoStr;
        if (pageNo == 1) {
            pageNoStr = "";
        } else {
            pageNoStr = "_" + pageNo;
        }
        return downloadBaseDir + "/Category/" + StringUtil.extractFromExclude(categoryLink, "Category:") + pageNoStr + ".htm";
    }


    public boolean isNeedDownloadArticle() {
        return needDownloadArticle;
    }

    public void setNeedDownloadArticle(boolean needDownloadArticle) {
        this.needDownloadArticle = needDownloadArticle;
    }

    public boolean isNeedPrintArticle() {
        return needPrintArticle;
    }

    public void setNeedPrintArticle(boolean needPrintArticle) {
        this.needPrintArticle = needPrintArticle;
    }

    public String getLinkPrefix() {
        return linkPrefix;
    }

    public void setLinkPrefix(String linkPrefix) {
        this.linkPrefix = linkPrefix;
    }
}
