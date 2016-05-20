package org.ailab.nlp.annotation;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2011-8-17
 * Time: 20:19:43
 * Desc: 标注类型常量定义
 */
public class AnnotationConstants {
    // annotation type
    public static final String CHAR = "Char";
    public static final String TOKEN = "Token";
    public static final String PARAGRAPH = "Paragraph";
    public static final String SENTENCE = "Sentence";
    public static final String SPLIT = "Split";

    public static final String LOOKUP = "Lookup";

    public static final String TITLE = "Title";

    public static final String PERSON = "Person";
    public static final String LOCATION = "Location";
    public static final String ORGANIZATION = "Organization";
    public static final String DATE = "Date";

    // annotation feature name
    public static final String CATEGORY = "category";

    // 种类（词/标点）
    public static final String KIND = "kind";

    // annotation feature name
    public static final String KIND_WORD = "word";
    public static final String KIND_PUNCTUATION = "punctuation";

    public static final HashMap<Character, String> map_entityType_charToString;


    static {
        map_entityType_charToString = new HashMap<Character, String>(6);
//        map_entityType_charToString.put(Constants.ENTITY_TYPE_PERSON, PERSON);
//        map_entityType_charToString.put(Constants.ENTITY_TYPE_ORGANIZATION, ORGANIZATION);
//        map_entityType_charToString.put(Constants.ENTITY_TYPE_LOCATION, LOCATION);
//        map_entityType_charToString.put(Constants.ENTITY_TYPE_WEAPON, Constants.ENTITY_TYPE_WEAPON_STRING);
//        map_entityType_charToString.put(Constants.ENTITY_TYPE_TREATY, Constants.ENTITY_TYPE_TREATY_STRING);
//        map_entityType_charToString.put(Constants.ENTITY_TYPE_OTHER, Constants.ENTITY_TYPE_OTHER_STRING);
    }
}
