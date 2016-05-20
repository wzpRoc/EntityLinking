package org.ailab.wimfra.util;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2012-7-4
 * Time: 15:29:06
 * Desc:
 */
public class TrimLowerKeyToObjectMap extends HashMap<String, Object> {
    @Override
    public Object get(Object key) {
        if(key == null){
            return null;
        }

        return super.get(normalize(key));
    }


    @Override
    public Object put(String key, Object value) {
        return super.put(normalize(key), value);
    }


    public String normalize(Object key){
        return normalize((String) key);
    }


    public String normalize(String key){
        if(key == null){
            return null;
        }

        return key.trim().toLowerCase();
    }
}
