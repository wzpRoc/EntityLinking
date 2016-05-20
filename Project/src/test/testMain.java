package test;

import wzp.wiki.PageCallbackHandler;
import wzp.wiki.WikiPage;
import wzp.wiki.WikiXMLParser;
import wzp.wiki.WikiXMLParserFactory;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-11-26
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class testMain {
    public static void main(String args[]) {
        WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser("E:\\Entity Linking FTP\\enwiki-20151102-pages-articles-multistream.xml");

        try {

            wxsp.setPageCallback(new PageCallbackHandler() {
                public void process(WikiPage page) {
                    System.out.println(page.getTitle());
                }
            });

            wxsp.parse();
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("hello world");
    }
}
