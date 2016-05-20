package org.ailab.wimfra.util.map;

import org.ailab.wimfra.util.KeyValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-6-19
 * Time: 22:03:06
 * Desc:
 */
public class MultiValueMap extends HashMap {
    public void add(Object key, Object value){
        Object originalValue = get(key);
        if(originalValue == null){
            put(key, value);
        } else {
            if(originalValue instanceof MultiValueByList) {
                ((MultiValueByList) originalValue).addValue(value);
            } else {
                put(key, new MultiValueByList(originalValue, value));
            }
        }
    }

    public List getList(Object key) {
        Object value = get(key);
        if(value == null) {
            return null;
        } else if(value instanceof MultiValueByList) {
            return ((MultiValueByList) value).toList();
        } else {
            ArrayList list = new ArrayList(1);
            list.add(value);
            return list;
        }
    }


    public static MultiValueMap fromKeyValueList(List<KeyValue> keyValueList) {
        MultiValueMap map = new MultiValueMap();
        for(KeyValue keyValue : keyValueList) {
            map.add(keyValue.getKey(), keyValue.getValue());
        }
        return map;
    }
}
