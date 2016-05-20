package org.ailab.nlp.parsing.english;

import gate.*;
import gate.creole.SerialAnalyserController;
import org.ailab.nlp.annotation.AnnotationConstants;
import org.ailab.nlp.gate.GateUtil;
import org.ailab.nlp.gate.PRNames;
import org.ailab.nlp.parsing.SentenceAnnotation;
import org.ailab.nlp.parsing.TokenAnnotation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2011-9-8
 * Time: 22:13:07
 * Desc:
 */
public class RussianUtil {
    public static final HashSet<Character> puncSetOfSentenceEnd;
    public static SerialAnalyserController controller;

    static {
        puncSetOfSentenceEnd = new HashSet<Character>();
        puncSetOfSentenceEnd.add('.');
        puncSetOfSentenceEnd.add(';');
        puncSetOfSentenceEnd.add('?');
        puncSetOfSentenceEnd.add('!');
    }


    /**
     * 分句
     *
     * @param text
     * @return
     */
    public synchronized static List<SentenceAnnotation> splitSentence(String text) throws Exception {
        if (text == null) {
            return null;
        }

        // 创建GATE控制器
        createController();

        // 创建文档、文集
        final Document doc = GateUtil.newDocument(text);
        final Corpus corpus = Factory.newCorpus("");
        //noinspection unchecked
        corpus.add(doc);

        // 使用GATE做解析
        controller.setCorpus(corpus);
        controller.execute();

        // 将GATE解析的结果转化为Annotation
        List<SentenceAnnotation> sentenceAnnotationList = new ArrayList<SentenceAnnotation>();
        final AnnotationSet sentenceAnnoSet = doc.getAnnotations().get(AnnotationConstants.SENTENCE);
        final ArrayList<Annotation> sentenceAnnoList = GateUtil.sort(sentenceAnnoSet);
        for (Annotation sentenceAnno : sentenceAnnoList) {
            SentenceAnnotation sentenceAnnotation = new SentenceAnnotation();
            sentenceAnnotationList.add(sentenceAnnotation);
            sentenceAnnotation.setGateAnnotation(sentenceAnno);
            sentenceAnnotation.setText(text.substring(sentenceAnnotation.start, sentenceAnnotation.end));
        }
        Factory.deleteResource(doc);
        Factory.deleteResource(corpus);

        return sentenceAnnotationList;
    }



    /**
     * 解析文本的“段落-句子-词”结构
     *
     * @param text
     * @return
     */
    public synchronized static List parseSenToken(String text) throws Exception {
        if (text == null) {
            return null;
        }

        // 创建GATE控制器
        createController();

        // 创建文档、文集
        final Document doc = GateUtil.newDocument(text);
        final Corpus corpus = Factory.newCorpus("");
        //noinspection unchecked
        corpus.add(doc);

        // 使用GATE做解析
        controller.setCorpus(corpus);
        controller.execute();

        // 将GATE解析的结果转化为“句子-词”结构
        // 1. 句子
        List<SentenceAnnotation> sentenceAnnotationList = new ArrayList<SentenceAnnotation>();
        final AnnotationSet sentenceAnnoSet = doc.getAnnotations().get(AnnotationConstants.SENTENCE);
        final ArrayList<Annotation> sentenceAnnoList = GateUtil.sort(sentenceAnnoSet);
        for (Annotation sentenceAnno : sentenceAnnoList) {
            SentenceAnnotation sentenceAnnotation = new SentenceAnnotation();
            sentenceAnnotationList.add(sentenceAnnotation);
            sentenceAnnotation.setGateAnnotation(sentenceAnno);
            sentenceAnnotation.setText(text.substring(sentenceAnnotation.start, sentenceAnnotation.end));

            // 2. 词
            List<TokenAnnotation> tokenAnnotationList = new ArrayList<TokenAnnotation>();
            sentenceAnnotation.tokenAnnotationList = tokenAnnotationList;
            final AnnotationSet tokenAnnoSet = doc.getAnnotations().get(AnnotationConstants.TOKEN, sentenceAnno.getStartNode().getOffset(), sentenceAnno.getEndNode().getOffset());
            final ArrayList<Annotation> tokenAnnoList = GateUtil.sort(tokenAnnoSet);
            for (Annotation tokenAnno : tokenAnnoList) {
                TokenAnnotation tokenAnnotation = new TokenAnnotation();
                tokenAnnotationList.add(tokenAnnotation);
                tokenAnnotation.setGateAnnotation(tokenAnno);
                tokenAnnotation.setText(text.substring(tokenAnnotation.start, tokenAnnotation.end));
            }
        }

        return sentenceAnnotationList;
    }


    /**
     * 创建GATE控制器
     *
     * @throws Exception
     */
    public static void createController() throws Exception {
        if (controller == null) {
            GateUtil.init();
            controller = GateUtil.createController();
            // 分词
            controller.add((ProcessingResource) Factory.createResource(PRNames.DEFAULT_TOKENISER, Factory.newFeatureMap()));
            // 分句
            controller.add((ProcessingResource) Factory.createResource(PRNames.SENTENCE_SPLITTER, Factory.newFeatureMap()));
        }
    }


    /**
     * 检查是否是英文字母
     * @param c
     * @return
     */
    public static boolean isEnglishLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }


}