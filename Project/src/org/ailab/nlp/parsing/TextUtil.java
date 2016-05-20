package org.ailab.nlp.parsing;

import org.ailab.nlp.LanguageConstants;
import org.ailab.nlp.parsing.chinese.ChineseUtil;
import org.ailab.nlp.parsing.english.EnglishUtil;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 13-5-5
 * Time: 下午9:11
 * Desc:
 */
public class TextUtil {
    /**
     * 分句，输出的结果中包含句子的文本
     */
    public static List<SentenceAnnotation> splitSentence(int languageId, String text) throws Exception {
        if (languageId == LanguageConstants.IDX_CHINESE) {
            return ChineseUtil.splitSentence(text);
        } else if (languageId == LanguageConstants.IDX_ENGLISH) {
            return EnglishUtil.splitSentence(text);
        } else {
            throw new Exception("Unsupported language: " + languageId);
        }
    }


    /**
     * 得到包含某位置的句子
     */
    public static SentenceAnnotation getSentence(int languageId, String text, int maxStartPosition, int minEndPosition) throws Exception {
        List<SentenceAnnotation> sentenceAnnotationList = splitSentence(languageId, text);
        if(sentenceAnnotationList == null) {
            return null;
        }

        for (SentenceAnnotation sentenceAnnotation : sentenceAnnotationList) {
            if (sentenceAnnotation.getStart() <= maxStartPosition && sentenceAnnotation.getEnd() >= minEndPosition) {
                return sentenceAnnotation;
            }
        }

        return null;
    }


}
