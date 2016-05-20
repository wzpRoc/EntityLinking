package org.ailab.nlp.annotation;

import org.ailab.nlp.match.EntryType;
import org.ailab.nlp.match.EntryTypeContainer;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2012-4-25
 * Time: 0:24:46
 * Desc:
 */
public class AnnotationUtil {
    /**
     * 获得标注的标注类别
     */
    public static String[] getAnnotationTypes(IAnnotation annotation) {
        String[] annotationTypes = null;
        final HashMap<String, Object> featureMap = annotation.getFeatureMap();
        if (featureMap != null) {
            EntryTypeContainer etc = (EntryTypeContainer) featureMap.get("entryTypeContainer");
            if(etc!=null){
                if(etc.nEntryType==1){
                    annotationTypes = new String[]{etc.entryType.majorType};
                } else {
                    final Object[] objects = etc.getEntryTypeSet().toArray();
                    annotationTypes = new String[objects.length];
                    for(int i=0;i<objects.length; i++){
                        annotationTypes[i] = ((EntryType) objects[i]).majorType;
                    }
                }
            }
        }
        if(annotationTypes==null){
            annotationTypes = new String[]{annotation.getType()};
        }
        return annotationTypes;
    }
}
