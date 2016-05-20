package org.ailab.wimfra.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 2012-6-19
 * Time: 22:03:06
 * Desc:
 */
public class ListMap extends HashMap<Object, List> {
    public void add(Object key, Object value){
        List list = get(key);
        if(list == null){
            list = new ArrayList();
            put(key, list);
        }
        list.add(value);
    }



    public static void add(Object key, Object value, Map map){
        List list = (List) map.get(key);
        if(list == null){
            list = new ArrayList();
            map.put(key, list);
        }
        list.add(value);
    }
}
