package org.ailab.wimfra.util.map;

import org.ailab.wimfra.util.KeyValue;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 2012-6-19
 * Time: 22:03:06
 * Desc:
 */
public class MultiValueSetMap extends HashMap {
    public void add(Object key, Object value){
        Object originalValue = get(key);
        if(originalValue == null){
            put(key, value);
        } else {
            if(originalValue instanceof MultiValueBySet) {
                ((MultiValueBySet) originalValue).addValue(value);
            } else {
                put(key, new MultiValueBySet(originalValue, value));
            }
        }
    }

    public Set getSet(Object key) {
        Object value = get(key);
        if(value == null) {
            return null;
        } else if(value instanceof MultiValueBySet) {
            return ((MultiValueBySet) value).toSet();
        } else {
            Set set = new HashSet(1);
            set.add(value);
            return set;
        }
    }


    public static MultiValueSetMap fromKeyValueSet(Set<KeyValue> keyValueSet) {
        MultiValueSetMap map = new MultiValueSetMap();
        for(KeyValue keyValue : keyValueSet) {
            map.add(keyValue.getKey(), keyValue.getValue());
        }
        return map;
    }
}
