package org.ailab.wimfra.util;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Time: 2010-11-11 20:53:54
 * Desc: 日志记录器的基类
 */
public class NumTable extends HashMap<Object, DoubleNum> {
    public void add(Object key, double num){
        DoubleNum value = get(key);
        if(value == null){
            value = new DoubleNum(num);
            put(key, value);
        } else {
            value.d += num;
        }
    }
}
