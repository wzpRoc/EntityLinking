package org.ailab.wimfra.util;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Lu Tingming
 * Time: 2010-11-11 20:53:54
 * Desc: 计数器，map的值除了计数以外，还包括一个对象
 */
public class CountTableWithObj extends HashMap<Object, ObjectAndCount>{
    /**
     * 根据key查找对象
     * 如果存在，那么计数加一
     * 否则，计数置为1，将对象存入
     * @param key
     * @param object
     * @return
     */
    public int add(Object key, Object object){
        // 根据key查找对象
        ObjectAndCount value = this.get(key);
        int count;
        if(value == null){
            // 对象不存在
            // 加入表
            this.put(key, new ObjectAndCount(object));
            count=1;
        }else {
            // 对象存在，计数加一
            value.count++;
            count = value.count;
        }

        return count;
    }

    public void print(){
        for (Map.Entry entry: this.entrySet()) {
            System.out.println(entry.getKey()+"\t\t"+((Int)entry.getValue()).i);
        }
    }
}