package org.ailab.nlp.parsing;

import org.ailab.nlp.annotation.Annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-3-27
 * Time: 10:52:47
 * Desc: 句子
 */
public class SentenceAnnotation extends Annotation {
    public List<TokenAnnotation> tokenAnnotationList;

    public SentenceAnnotation() {
    }

    public SentenceAnnotation(int start, int end) {
        super(start, end);
    }

    public void addTokenAnnotation(TokenAnnotation tokenAnnotation) {
        if(tokenAnnotationList == null) {
            tokenAnnotationList = new ArrayList<TokenAnnotation>();
        }
        tokenAnnotationList.add(tokenAnnotation);
    }
}