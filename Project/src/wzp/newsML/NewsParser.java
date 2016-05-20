package wzp.newsML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xmlParse.DOMParser;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-12-19
 * Time: 下午6:50
 * To change this template use File | Settings | File Templates.
 */

public class NewsParser {

    private DOMParser parser = null;
    private Document document = null;
    private News news = new News();

    public NewsParser(String filePath) {
        parser = new DOMParser();
        document = parser.parse(filePath);
    }

    public void parseText() {
        StringBuilder sb = new StringBuilder();
        Element rootElement = document.getDocumentElement();
        NodeList headlineNodeList = rootElement.getElementsByTagName("headline");
        if(headlineNodeList != null && headlineNodeList.getLength()>0)
        {
            Element headlineElement = (Element)headlineNodeList.item(0);
            sb.append(headlineElement.getTextContent());
            sb.append("\n");
        }
        NodeList bylineNodeList = rootElement.getElementsByTagName("byline");
        if(bylineNodeList != null && bylineNodeList.getLength()>0)
        {
            Element bylineElement = (Element)bylineNodeList.item(0);
            sb.append(bylineElement.getTextContent());
            sb.append("\n");
        }
        NodeList datelineNodeList = rootElement.getElementsByTagName("dateline");
        if(datelineNodeList != null && datelineNodeList.getLength()>0)
        {
            Element datelineElement = (Element)datelineNodeList.item(0);
            sb.append(datelineElement.getTextContent());
            sb.append("\n");
        }
        NodeList textNodeList = rootElement.getElementsByTagName("text");
        if(textNodeList != null && textNodeList.getLength()>0)
        {
            Element textElement = (Element)textNodeList.item(0);
            sb.append(textElement.getTextContent());
        }
        news.setText(sb.toString());
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
