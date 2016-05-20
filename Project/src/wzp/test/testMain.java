package wzp.test;

import nlp.stanford.StanfordUtil;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import wzp.MentionML.MentionProcessTask;
import wzp.candidateEntityML.LocalProbExtractTask;
import wzp.newsML.NewsExtractTask;
import wzp.wiki.PageCallbackHandler;
import wzp.wiki.WikiPage;
import wzp.wiki.WikiXMLParser;
import wzp.wiki.WikiXMLParserFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-11-26
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class testMain {

//    public static void main(String args[]) {
//        WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser("F:\\Wikipedia\\pages-articles\\B.xml");
//
//        try {
//
//            wxsp.setPageCallback(new PageCallbackHandler() {
//                public void process(WikiPage page) {
//                    System.out.println(page.getTitle());
//                }
//            });
//
//            wxsp.parse();
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("hello world");
//    }

    public static void main(String args[]) throws Exception{

        NewsExtractTask task1 = new NewsExtractTask();
        //task1.ExtractNewsForMention("E:\\Entity Linking FTP\\news\\Volume1 English", false);
        //task1.ExtractNewsForWord("E:\\Entity Linking FTP\\news\\Volume1 English", false);

//        MentionProcessTask task2 = new MentionProcessTask();
//        task2.mentionProcess(false);

        LocalProbExtractTask task3 = new LocalProbExtractTask();
        task3.extractProbOfMentionToEntity(false);

//        String test = "People's. Republic of China";
//        List<String> c = StanfordUtil.Tokenize(test);
    }

}
