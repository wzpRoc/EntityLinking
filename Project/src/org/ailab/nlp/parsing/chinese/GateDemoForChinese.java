package org.ailab.nlp.parsing.chinese;

import gate.*;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;

/**
 * User: Lu Tingming
 * Date: 2011-8-26
 * Time: 16:52:47
 * Desc:
 */
public class GateDemoForChinese {
    public static void main(String[] args) throws GateException {
        String text = "本报讯（记者易靖）记者昨天从铁道部获悉，铁道部新闻发言人王勇平不再担任铁道部新闻发言人、政治部宣传部部长职务。\n" +
                "\n" +
                "　　王勇平原任铁道部政治部副主任、政治部宣传部部长、铁道部新闻发言人职务。铁道部相关负责人昨晚表示，王勇平不再担任铁道部新闻发言人、政治部宣传部部长职务。他强调，“这不是免职或被停职，而是正常的职务变动，王勇平的级别待遇没变，调到哪个部门还没定。”对于王勇平是否仍担任铁道部政治部副主任一职，该负责人并未透露。\n" +
                "\n" +
                "　　有知情人士透露，接任铁道部新闻发言人职务的，可能是哈尔滨铁路局党委书记韩江平。他表示，这一调整应与王勇平在温州事故新闻发布会表现欠妥有关。\n" +
                "\n" +
                "　　7月24日晚，在“7·23”动车追尾事故发生26个小时后，铁道部在温州水心饭店召开新闻发布会，王勇平通报了事故情况，并回答了部分记者的提问。\n" +
                "\n" +
                "　　在回答“为什么要掩埋车头”时，王勇平解释，参与救援的人告诉他，为填平泥潭，方便救援，并称“他们是这么说的，至于你信不信（由你），我反正信了”。在回答记者“为何在宣布没有生命体征、停止救援后，又发现小女孩项炜伊时”，王勇平回答：“这只能说是生命的奇迹。”这些回答引发网友对铁道部的质疑，并成为网络流行语，该句式也被称为“高铁体”。\n" +
                "\n" +
                "　　此后，王勇平返京，未再在公众面前露面，也未再接受采访。日前曾有传言称，王勇平的工作已被调整，由新的新闻发言人代替其工作；另一种说法是，王勇平回京后即被停职。7月29日，铁道部新闻处有关负责人证实，王勇平仍为铁道部新闻发言人，工作没有调整，“他不是事故责任人，也不是救援人员，只是做了他职责范围内的事情（开新闻发布会），没有问题”。\n" +
                "\n" +
                "　　知情人士介绍，王勇平职务的调整可能就是近几天做出的决定。\n" +
                "abc\n" +
                "ab\n" +
                "a\n" +
                "\n" +
                "";

        // process
        SerialAnalyserController controller = GateUtilForChinese.newController();
        // data
        Corpus corpus = Factory.newCorpus("wzp/newsML/test");
        Document document = Factory.newDocument(text);
        corpus.add(document);

        controller.setCorpus(corpus);
        controller.execute();


        // get annotation
        AnnotationSet annoSet = document.getAnnotations().get("TokenAnnotation");
        for(Annotation anno : annoSet){
            final String category = (String) anno.getFeatures().get("category");
            System.out.println(
                    anno.getStartNode().getOffset()+"\t"+
                    anno.getEndNode().getOffset()+"\t"+
                    anno.getFeatures().get("string")+"\t"+
                            category +"\t"+
                            IctclasUtil.getStructByCategory(category).toString()
            );
        }
    }
}
