package org.ailab.nlp.match;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2011-8-24
 * Time: 19:35:43
 * Desc:
 */
public class EntryType {
    public String majorType;
    public String minorType;

    public EntryType(String majorType, String minorType) {
        this.majorType = majorType;
        this.minorType = minorType;
    }

    public boolean equals(EntryType entryType){
        if(majorType == null){
            return entryType.majorType==null;
        } else {
            if(majorType.equals(entryType.majorType)){
                if(minorType == null){
                    return entryType.minorType == null;
                } else {
                    return minorType.equals(entryType.minorType);
                }
            } else {
                return false;
            }
        }
    }

    public HashMap<String, Object> toFeatureMap() {
        HashMap<String, Object> featureMap = new HashMap<String, Object>(2);
        featureMap.put("majorType", majorType);
        if(minorType!=null){
            featureMap.put("minorType", minorType);
        }
        return featureMap;
    }
}
