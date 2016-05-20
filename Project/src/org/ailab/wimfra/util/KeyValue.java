package org.ailab.wimfra.util;

/**
 * User: Lu Tingming
 * Date: 2012-7-4
 * Time: 15:48:57
 * Desc:
 */
public class KeyValue {
    public String key;
    public String value;

    public KeyValue() {
    }

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
