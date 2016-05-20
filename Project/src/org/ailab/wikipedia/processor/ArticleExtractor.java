package org.ailab.wikipedia.processor;

import org.ailab.nlp.TSChineseConverter;
import org.ailab.webInformationExtraction.CvNodeFilter;
import org.ailab.webInformationExtraction.HtmlUtil;
import org.ailab.webInformationExtraction.Parser;
import org.ailab.webInformationExtraction.SimpleTagNameFilter;
import org.ailab.wikipedia.Util;
import org.ailab.wikipedia.wim.article.Article;
import org.ailab.wikipedia.wim.article.ArticleBL;
import org.ailab.wikipedia.wim.infobox.Infobox;
import org.ailab.wikipedia.wim.infoboxItem.InfoboxItem;
import org.ailab.wimfra.util.StringUtil;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-28
 * Time: 下午2:13
 */
public class ArticleExtractor {
    protected static Pattern birthdayPattern = Pattern.compile("(\\d{4}-\\d{1,2}-\\d{1,2})");
    protected static Pattern birthYearPattern = Pattern.compile("(\\d{4})");
    protected String downloadBaseDir;
    protected Article article;
    protected Parser parser;
    protected Tag abstNode;

    public ArticleExtractor(Article article) {
        this.article = article;
    }

    public void extract() throws Exception {
        String path = Util.getArticlePath(downloadBaseDir, article.getLink());

        parser = new Parser();
        try{
            parser.setPath(path);
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }

        final String title = TSChineseConverter.toSimplifiedChinese(article.getTitle());
        article.setTitle(title);
        int idx = title.indexOf("_(");
        if(idx>0) {
            article.setNormalizedTitle(title.substring(0, idx));
        } else {
            article.setNormalizedTitle(title);
        }

        // infobox
        NodeList infoboxTableList = parser.find(new CvNodeFilter("table").addAttributeRegex("class", "infobox.*"));
        if (infoboxTableList != null && infoboxTableList.size() > 0) {
            // image
            Tag imageTag;
            imageTag = parser.findFirstTag(new CvNodeFilter("span", "class", "photo"));
            String photoLink = null;
            if (imageTag != null) {
                photoLink = imageTag.toPlainTextString().trim();
            } else {
                imageTag = (Tag) parser.findFirstOffspring(infoboxTableList, new CvNodeFilter("img").andHasParentFilter(new CvNodeFilter("a", "class", "image")));
                if (imageTag != null) {
                    photoLink = imageTag.getAttribute("src");
                }
            }
            if (photoLink != null) {
                if (photoLink.startsWith("//")) {
                    photoLink = "http:" + photoLink;
                }
                article.setPhotoLink(URLDecoder.decode(photoLink));
            }

            // infobox
            article.setInfobox(new Infobox());
            for (Node infoboxTable : infoboxTableList.toNodeArray()) {
                processInfoboxTable(infoboxTable);
            }

            // abst
            Node lastInfobox = infoboxTableList.elementAt(infoboxTableList.size() - 1);
            abstNode = parser.findFirstTagAfter(lastInfobox, "p");
        }

        // abst
        if(abstNode == null) {
            abstNode = parser.findFirstTag(new CvNodeFilter("p").andHasParentFilter(new CvNodeFilter("div", "id", "mw-content-text")));
        }
        if (abstNode != null) {
            String abst = abstNode.toPlainTextString().trim();
            abst = TSChineseConverter.toSimplifiedChinese(abst);
            abst = replaceReferenceMark(abst);
            abst = replaceSpecialChar(abst);
            article.setAbst(abst);
        }

    }

    protected String replaceReferenceMark(String s) {
        if(s == null) return s;
        return s.replaceAll("\\[\\d+\\]", "");
    }

    protected void processInfoboxTable(Node infoboxTable) throws Exception {
        NodeList rowList = parser.findChildIn(infoboxTable, SimpleTagNameFilter.trFilter);
        if (rowList != null) {
            for (Node rowNode : rowList.toNodeArray()) {
                final InfoboxItem infoboxItem = extractInfoboxItem(parser, rowNode);
                if (infoboxItem != null) {
                    article.getInfobox().add(infoboxItem);
                }
            }
        }
    }

    public InfoboxItem extractInfoboxItem(Parser parser, Node rowNode) throws Exception {
        NodeList tdList = parser.findChildIn(rowNode, SimpleTagNameFilter.thtdFilter);
        if (tdList != null && tdList.size() == 2) {
            // title
            final Node titleTd = tdList.elementAt(0);
            String title = titleTd.toPlainTextString().trim();
            title = TSChineseConverter.toSimplifiedChinese(title);

            // value
            final Node valueTd = tdList.elementAt(1);
            String value = valueTd.toPlainTextString();
            value = HtmlUtil.htmlToText(value);
            value = value.trim();
            value = replaceSpecialChar(value);
            value = replaceReferenceMark(value);
            value = TSChineseConverter.toSimplifiedChinese(value);

            // normalizedValue
            String normalizedValue;
            if ("出生".equals(title)) {
                normalizedValue = StringUtil.extractFirst(value, birthdayPattern);
                if (normalizedValue == null) {
                    normalizedValue = StringUtil.extractFirst(value, birthYearPattern);
                }
            } else {
                LinkTag firstLinkTag = (LinkTag) parser.findFirstOffspringTagIn(valueTd, "a");
                if (firstLinkTag != null) {
                    String href = firstLinkTag.getAttribute("href");
                    if (href.startsWith("#")) {
                        normalizedValue = value;
                    } else {
                        if (href.startsWith("/wzp/wiki/")) {
                            href = href.substring(6);
                        }
                        if (href.startsWith("%")) {
                            href = URLDecoder.decode(href);
                        }
                        normalizedValue = href;
                    }
                } else {
                    normalizedValue = value;
                }
            }
            if (normalizedValue != null) {
                normalizedValue = TSChineseConverter.toSimplifiedChinese(normalizedValue);
                normalizedValue = replaceReferenceMark(normalizedValue);
                if (value.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
                    normalizedValue = value.replace('年', '-').replace('月', '-').replace("日", "");
                } else if (normalizedValue.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
                    normalizedValue = normalizedValue.replace('年', '-').replace('月', '-').replace("日", "");
                }
            }

            return postProcessInfoboxItem(title, value, normalizedValue);
        } else {
            return null;
        }
    }

    protected InfoboxItem postProcessInfoboxItem(String title, String value, String normalizedValue) {
        return new InfoboxItem(title, value, normalizedValue);
    }

    public static String replaceSpecialChar(String s) {
        return s
                .replace("𧒽", "?")
                .replace("𡛔", "钙")
                .replace("𠊎", "涯")
                .replace("𤷪", "孟")
                .replace("𣈱", "畅");
    }

    public String getDownloadBaseDir() {
        return downloadBaseDir;
    }

    public void setDownloadBaseDir(String downloadBaseDir) {
        this.downloadBaseDir = downloadBaseDir;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }


    protected String decode(String link) {
        if(link == null) {
            return null;
        }
        return URLDecoder.decode(link);
    }


    public static void main(String[] args) throws SQLException {
        Article article = (Article) (new ArticleBL()).get(28);

    }
}
