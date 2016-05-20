package org.ailab.wimfra.util;

/**
 * User: Lu Tingming
 * Date: 2012-6-18
 * Time: 16:45:14
 * Desc:
 */
public class KeyAndValue {
    public Object key;
    public Object value;

    public KeyAndValue(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
