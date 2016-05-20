package org.ailab.nlp.parsing;

import org.ailab.nlp.LanguageConstants;
import org.ailab.nlp.parsing.chinese.ChineseUtil;
import org.ailab.nlp.parsing.chinese.JapaneseUtil;
import org.ailab.nlp.parsing.english.EnglishUtil;
import org.ailab.nlp.parsing.english.RussianUtil;
import org.ailab.wimfra.util.StringUtil;

import java.util.List;

/**
 * User: lutingming
 * Date: 13-7-25
 * Time: 上午11:58
 */
public class ParsingUtil {
    /**
     * 设置词的句子号
     */
    public static void setSentenceSeqToToken(List<SentenceAnnotation> sentenceAnnotationList, List<TokenAnnotation> tokenAnnotationList) {
        setSentenceSeqToToken(sentenceAnnotationList, tokenAnnotationList, false);
    }


    /**
     * 设置词的句子号
     */
    public static void setSentenceSeqToToken(List<SentenceAnnotation> sentenceAnnotationList, List<TokenAnnotation> tokenAnnotationList, boolean addTokenToSentence) {
        if (tokenAnnotationList == null) return;

        for (int i_token = 0; i_token < tokenAnnotationList.size(); i_token++) {
            TokenAnnotation token = tokenAnnotationList.get(i_token);
            boolean found = false;
            for (int i_sentence = 0; i_sentence < sentenceAnnotationList.size(); i_sentence++) {
                SentenceAnnotation sen = sentenceAnnotationList.get(i_sentence);
                if (token.getStart() >= sen.getStart() && token.getEnd() <= sen.getEnd()) {
                    token.setSentenceSeq(i_sentence);
                    if (addTokenToSentence) {
                        sen.addTokenAnnotation(token);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                token.setSentenceSeq(-1);
            }
        }
    }


    /**
     * 把词加到句子中去
     */
    public static void addTokenToSentence(List<SentenceAnnotation> sentenceAnnotationList, List<TokenAnnotation> tokenAnnotationList) {
        if (tokenAnnotationList == null) return;

        for (int i_sentence = 0; i_sentence < sentenceAnnotationList.size(); i_sentence++) {
            SentenceAnnotation sen = sentenceAnnotationList.get(i_sentence);
            for (int i_token = 0; i_token < tokenAnnotationList.size(); i_token++) {
                TokenAnnotation token = tokenAnnotationList.get(i_token);
                if (token.getStart() >= sen.getStart() && token.getEnd() <= sen.getEnd()) {
                    token.setSentenceSeq(i_sentence);
                    sen.addTokenAnnotation(token);
                }
            }
        }
    }


    /**
     * 分句
     */
    public static List<SentenceAnnotation> splitSentence(String text, int languageId) throws Exception {
        if(StringUtil.isEmpty(text)) {
            return null;
        } else {
            switch (languageId) {
                case LanguageConstants.IDX_CHINESE:
                case LanguageConstants.IDX_TRADITIONAL_CHINESE:
                    return ChineseUtil.splitSentence(text);
                case LanguageConstants.IDX_ENGLISH:
                    return EnglishUtil.splitSentence(text);
                case LanguageConstants.IDX_RUSSIAN:
                    return RussianUtil.splitSentence(text);
                case LanguageConstants.IDX_JAPANESE:
                    return JapaneseUtil.splitSentence(text);
                default:
                    throw new Exception("Can not handle language: "+languageId);
            }
        }
    }
}
