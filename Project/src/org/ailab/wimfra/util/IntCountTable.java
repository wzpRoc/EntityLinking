package org.ailab.wimfra.util;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Lu Tingming
 * Time: 2010-11-11 20:53:54
 * Desc: 计数器（键为整数）
 */
public class IntCountTable extends HashMap<Integer, Int>{
    public int add(int key){
        Int value = this.get(key);
        int i;
        if(value == null){
            i=1;
            this.put(key, new Int(i));
        }else {
            value.i++;
            i = value.i;
        }

        return i;
    }

    public void print(){
        for (Map.Entry entry: this.entrySet()) {
            System.out.println(entry.getKey()+"\t\t"+((Int)entry.getValue()).i);
        }
    }
}