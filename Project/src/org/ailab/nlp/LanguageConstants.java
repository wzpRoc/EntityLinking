package org.ailab.nlp;

import org.ailab.common.language.Language;
import org.ailab.common.language.LanguageBL;
import org.ailab.nlp.parsing.chinese.ChineseUtil;
import org.ailab.nlp.parsing.english.EnglishUtil;
import org.ailab.wimfra.util.Config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 14-1-14
 * Time: 下午2:00
 */
public class LanguageConstants {
    public static final int IDX_CHINESE = 1;
    public static final int IDX_TRADITIONAL_CHINESE = 2;
    public static final int IDX_ENGLISH = 3;
    public static final int IDX_RUSSIAN = 4;
    public static final int IDX_JAPANESE = 5;

    public static final int[] languages = new int[]{
            IDX_CHINESE, IDX_TRADITIONAL_CHINESE, IDX_ENGLISH, IDX_RUSSIAN, IDX_JAPANESE
    };
    public static List<Language> languageList;
    public static final int[] languagesEnabled;

    public static final HashSet<Character> puncSetOfSentenceEnd;
    public static String ALL_LANGUAGE_ID = "1,2,3,4,5";

    static {
        languageList = (new LanguageBL()).getList();

        ArrayList<Integer> intList = new ArrayList<Integer>();
        if (Config.isEnableSimplifiedChinese()) {
            intList.add(IDX_CHINESE);
        }
        if (Config.isEnableTraditionalChinese()) {
            intList.add(IDX_TRADITIONAL_CHINESE);
        }
        if (Config.isEnableEnglish()) {
            intList.add(IDX_ENGLISH);
        }
        if (Config.isEnableRussian()) {
            intList.add(IDX_RUSSIAN);
        }
        if (Config.isEnableJapanese()) {
            intList.add(IDX_JAPANESE);
        }

        languagesEnabled = new int[intList.size()];
        for (int i = 0; i < intList.size(); i++) {
            languagesEnabled[i] = intList.get(i);
        }

        puncSetOfSentenceEnd = new HashSet<Character>();
        puncSetOfSentenceEnd.addAll(ChineseUtil.puncSetOfSentenceEnd);
        puncSetOfSentenceEnd.addAll(EnglishUtil.puncSetOfSentenceEnd);
    }

    public static String getLanguageCode(int languageId) {
        if (languageId == IDX_CHINESE) {
            return "zh";
        } else if (languageId == IDX_ENGLISH) {
            return "en";
        } else if (languageId == IDX_RUSSIAN) {
            return "ru";
        } else if (languageId == IDX_TRADITIONAL_CHINESE) {
            return "zf";
        } else if (languageId == IDX_JAPANESE) {
            return "ja";
        }
        return null;
    }

    public static int getLanguageId(String languageCode) {
        if ("zh".equalsIgnoreCase(languageCode)) {
            return IDX_CHINESE;
        } else if ("en".equalsIgnoreCase(languageCode)) {
            return IDX_ENGLISH;
        } else if ("ru".equalsIgnoreCase(languageCode)) {
            return IDX_RUSSIAN;
        } else if ("zf".equalsIgnoreCase(languageCode)) {
            return IDX_TRADITIONAL_CHINESE;
        } else if ("ja".equalsIgnoreCase(languageCode)) {
            return IDX_JAPANESE;
        }
        return 0;
    }

    public static String getLanguageInChinese(int languageId) {
        if (languageId == IDX_CHINESE) {
            return "中文简体";
        } else if (languageId == IDX_ENGLISH) {
            return "英文";
        } else if (languageId == IDX_RUSSIAN) {
            return "俄文";
        } else if (languageId == IDX_TRADITIONAL_CHINESE) {
            return "中文繁体";
        } else if (languageId == IDX_JAPANESE) {
            return "日文";
        }
        return null;
    }

    public static int getLanguageIdByName(String languageName) {
        if (languageName.equals("中文简体")) {
            return IDX_CHINESE;
        } else if (languageName.equals("英文")) {
            return IDX_ENGLISH;
        } else if (languageName.equals("俄文")) {
            return IDX_RUSSIAN;
        } else if (languageName.equals("中文繁体")) {
            return IDX_TRADITIONAL_CHINESE;
        } else if (languageName.equals("日文")) {
            return IDX_JAPANESE;
        }
        return 0;
    }


    /**
     * 得到指定语种的中文短名称
     */
    public static String getShortNameInChinese(int languageId) {
        if (languageId == IDX_CHINESE) {
            return "中简";
        } else if (languageId == IDX_ENGLISH) {
            return "英";
        } else if (languageId == IDX_RUSSIAN) {
            return "俄";
        } else if (languageId == IDX_TRADITIONAL_CHINESE) {
            return "中繁";
        } else if (languageId == IDX_JAPANESE) {
            return "日";
        }
        return String.valueOf(languageId);
    }

    public static String getTwoChineseCharacter(int languageId) {
        if (languageId == IDX_CHINESE) {
            return "中简";
        } else if (languageId == IDX_ENGLISH) {
            return "英文";
        } else if (languageId == IDX_RUSSIAN) {
            return "俄文";
        } else if (languageId == IDX_TRADITIONAL_CHINESE) {
            return "中繁";
        } else if (languageId == IDX_JAPANESE) {
            return "日文";
        }
        return String.valueOf(languageId);
    }


    /**
     * 判断是否为句子结束
     */
    public static boolean isEndMarkOfSentence(char c) {
        return puncSetOfSentenceEnd.contains(c);
    }


    public static boolean isWestLanguage(int languageId) {
        return languageId == IDX_ENGLISH || languageId == IDX_RUSSIAN;
    }

}
