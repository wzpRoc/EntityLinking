package org.ailab.nlp.gate;


import org.ailab.nlp.LanguageConstants;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2011-8-28
 * Time: 14:56:38
 * Desc:
 */
public class PRNames {
    private static HashMap<String, String> typeToNameMap;

    public static final String DEFAULT_TOKENISER = "gate.creole.tokeniser.DefaultTokeniser";
    public static final String SENTENCE_SPLITTER = "gate.creole.splitter.SentenceSplitter";
    public static final String DEFAULT_GAZETTEER = "gate.creole.gazetteer.DefaultGazetteer";
    public static final String POS_TAGGER = "gate.creole.POSTagger";
    public static final String MORPHOLOGICAL_ANALYSER = "gate.creole.morph.Morph";
    public static final String ANNIETransducer = "gate.creole.ANNIETransducer";
    public static final String JAPE_TRANSDUCER = "gate.creole.Transducer";
    public static final String ANNOTATION_SET_TRANSFER = "gate.creole.annotransfer.AnnotationSetTransfer";
    public static final String STANFORD_NER = "org.ailab.irica.analysis.ner.StanfordNERForGATE";

    public static final String CHINESE_PARSER = "org.ailab.irica.parsing.chinese.ChineseParserForGATE";
    public static final String CHINESE_GAZETTEER = "org.ailab.irica.analysis.gazetteer.ChineseGazetteerForGATE";

    public static final String DB_DE_LOADER = "org.ailab.irica.analysis.ner.DBNELoaderForGATE";

    static{
        typeToNameMap = new HashMap<String, String>();

        // gazetteer
        typeToNameMap.put(LanguageConstants.IDX_CHINESE+"_"+PRTypes.GAZETTEER, "gate.creole.gazetteer.DefaultGazetteer");
        typeToNameMap.put(LanguageConstants.IDX_ENGLISH+"_"+PRTypes.GAZETTEER, "org.ailab.irica.analysis.gazetteer.ChineseGazetteerForGATE");
    }

    public static String getPRName(String prType, int languageId){
        if(PRTypes.JAPE_TRANSDUCER.equals(prType)){
            return JAPE_TRANSDUCER;
        } else {
            final String prName = typeToNameMap.get(languageId + "_" + prType);
            if(prName!=null){
                return prName;
            } else {
                return prType;
            }
        }
    }
}
