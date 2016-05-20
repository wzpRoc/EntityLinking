package org.ailab.ml;

import org.ailab.wimfra.util.NumberUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午4:48
 * Desc:
 */
public class WordVector extends HashMap<String, Double> {
    public WordVector() {
    }

    public WordVector(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Double> entry : entrySet()) {
            sb.append(entry).append("->").append(NumberUtil.format(entry.getValue(), 5, 5)).append("\n");
        }
        return sb.toString();
    }
}
