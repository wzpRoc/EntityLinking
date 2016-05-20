package org.ailab.nlp.parsing;

import org.ailab.nlp.annotation.Annotation;

/**
 * User: Lu Tingming
 * Date: 2012-3-27
 * Time: 10:52:47
 * Desc: 词
 */
public class TokenAnnotation extends Annotation {
    public TokenAnnotation() {
    }

    public TokenAnnotation(String type, int start, int end) {
        super(type, start, end);
    }

    public TokenAnnotation(String type, int start, int end, String string) {
        super(type, start, end, string);
    }
}