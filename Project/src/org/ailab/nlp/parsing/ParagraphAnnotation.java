package org.ailab.nlp.parsing;


import org.ailab.nlp.annotation.Annotation;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-3-27
 * Time: 10:52:47
 * Desc: 段落
 */
public class ParagraphAnnotation extends Annotation {
    public List<SentenceAnnotation> sentenceAnnotationList;
}
