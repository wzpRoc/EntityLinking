package org.ailab.nlp.parsing.chinese;

import gate.AnnotationSet;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.SimpleFeatureMapImpl;
import org.ailab.nlp.annotation.Annotation;
import org.ailab.nlp.annotation.AnnotationConstants;
import org.ailab.nlp.annotation.IAnnotation;
import org.ailab.nlp.parsing.SentenceAnnotation;
import org.ailab.nlp.parsing.TokenAnnotation;

import java.util.ArrayList;
import java.util.List;


/**
 * User: Lu Tingming
 * Date: 2011-8-17
 * Time: 13:02:33
 * Desc: 实现GATE的接口
 */
public class ChineseParserForGATE extends AbstractLanguageAnalyser implements ProcessingResource {
    private static final long serialVersionUID = -3062171258011850283L;

    protected String homeOfIctclas;
    protected String pathOfUserDic;
    protected String encodingOfUserDic;
    protected String annotationSetName;
    protected String outputAnnotationSetName;
    protected AnnotationSet annotationSet;

    private ChineseParser parser;


    /**
     * Initialize the Parser resource.  In particular, load the trained data
     * file.
     */
    public Resource init() throws ResourceInstantiationException {
        parser = new ChineseParser();
        try {
            parser.init(homeOfIctclas, pathOfUserDic, encodingOfUserDic);
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceInstantiationException(e);
        }
    }


    /**
     * Parse the current document.  (This is the principal
     * method called by a CorpusController.)
     */
    public void execute() throws ExecutionException {
        // annotationSet = convertASName(annotationSetName);
        AnnotationSet outAnnotationSet = convertASName(outputAnnotationSetName);
        try {
            final String text = document.getContent().toString();
            // 调用chineseParser解析

            // 1. 分段
            ArrayList<Annotation> paragraphAnnotationList = ChineseUtil.splitParagraph(text);
            // 转换为GATE的标注
            if (paragraphAnnotationList == null) {
                // System.err.println("paragraphAnnotationList == null");
            } else {
                for (int i = 0; i < paragraphAnnotationList.size(); i++) {
                    Annotation annotation = paragraphAnnotationList.get(i);
                    final SimpleFeatureMapImpl featureMap = new SimpleFeatureMapImpl();
                    featureMap.put("seqInDoc", i);
                    outAnnotationSet.add(
                            (long) annotation.start,
                            (long) annotation.end,
                            AnnotationConstants.PARAGRAPH,
                            featureMap
                    );
                }
            }

            // 2. 分句
            List<SentenceAnnotation> sentenceAnnotationList = ChineseUtil.splitSentence(text);
            // 转换为GATE的标注
            for (int i = 0; i < sentenceAnnotationList.size(); i++) {
                Annotation annotation = sentenceAnnotationList.get(i);
                final SimpleFeatureMapImpl featureMap = new SimpleFeatureMapImpl();
                featureMap.put("seqInDoc", i);
                // 加入句子标注
                outAnnotationSet.add(
                        (long) annotation.start,
                        (long) annotation.end,
                        AnnotationConstants.SENTENCE,
                        featureMap
                );

                // 加入句子分隔符标注
                Annotation sentenceAnno = sentenceAnnotationList.get(i);
                long startOfSplit = -1;
                long endOfSplit = -1;
                if (sentenceAnno.end - sentenceAnno.start - 1 > 0) {
                    // 如果最后一个字符是句子分隔符，那么split要标在这个字符上
                    char endingChar = text.charAt(sentenceAnno.end - 1);
                    if (ChineseUtil.isEndMarkOfSentence(endingChar)) {
                        startOfSplit = sentenceAnno.end - 1;
                        endOfSplit = sentenceAnno.end;
                    }
                }
                if (startOfSplit == -1 || endOfSplit == -1) {
                    if (i < sentenceAnnotationList.size() - 1) {
                        // 如果不是最后一句
                        startOfSplit = annotation.end;
                        endOfSplit = sentenceAnnotationList.get(i + 1).start;
                    }
                }
                if (startOfSplit != -1 && endOfSplit != -1) {
                    outAnnotationSet.add(
                            startOfSplit,
                            endOfSplit,
                            AnnotationConstants.SPLIT,
                            new SimpleFeatureMapImpl()
                    );
                }
            }

            // 3. 分词、词性标注
            List<TokenAnnotation> ictclasAnnotationList = parser.parseByInstance(text);
            // 转换为GATE的标注
            for (IAnnotation annotation : ictclasAnnotationList) {
                TokenAnnotation ictclasAnnotation = (TokenAnnotation) annotation;
                final SimpleFeatureMapImpl map = new SimpleFeatureMapImpl();

                final IctclasCategoryStruct categoryStruct = IctclasUtil.getStructByCategory(ictclasAnnotation.getType());

                // 种类（词/标点）
                if (IctclasUtil.POS_PUNCTUATION.equals(categoryStruct.category1)) {
                    map.put(AnnotationConstants.KIND, AnnotationConstants.KIND_PUNCTUATION);
                } else {
                    map.put(AnnotationConstants.KIND, AnnotationConstants.KIND_WORD);
                }

                // 词性
                map.put(AnnotationConstants.CATEGORY, ictclasAnnotation.getType());

                // 词形
                map.put("string", ictclasAnnotation.string);
                outAnnotationSet.add(
                        (long) ictclasAnnotation.start,
                        (long) ictclasAnnotation.end,
                        AnnotationConstants.TOKEN,
                        map
                );
            }

            // 分字
            for (int i = 0; i < text.length(); i++) {
                final SimpleFeatureMapImpl map = new SimpleFeatureMapImpl();

                outAnnotationSet.add(
                        (long) i,
                        (long) i + 1,
                        AnnotationConstants.CHAR,
                        map
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecutionException(e);
        }
    }


    /**
     * Re-initialize the Parser resource.  In particular, reload the trained
     * data file.
     */
    public void reinit() throws ResourceInstantiationException {
        init();
    }

    protected AnnotationSet convertASName(String name) {
        if ((name == null) || name.equals("")) {
            return document.getAnnotations();
        }

        /* implied else */
        return document.getAnnotations(name);
    }

    public String getOutputAnnotationSetName() {
        return outputAnnotationSetName;
    }

    public void setOutputAnnotationSetName(String outputAnnotationSetName) {
        this.outputAnnotationSetName = outputAnnotationSetName;
    }

    public String getHomeOfIctclas() {
        return homeOfIctclas;
    }

    public void setHomeOfIctclas(String homeOfIctclas) {
        this.homeOfIctclas = homeOfIctclas;
    }

    public String getAnnotationSetName() {
        return annotationSetName;
    }

    public void setAnnotationSetName(String annotationSetName) {
        this.annotationSetName = annotationSetName;
    }

    public String getPathOfUserDic() {
        return pathOfUserDic;
    }

    public void setPathOfUserDic(String pathOfUserDic) {
        this.pathOfUserDic = pathOfUserDic;
    }

    public String getEncodingOfUserDic() {
        return encodingOfUserDic;
    }

    public void setEncodingOfUserDic(String encodingOfUserDic) {
        this.encodingOfUserDic = encodingOfUserDic;
    }
}
