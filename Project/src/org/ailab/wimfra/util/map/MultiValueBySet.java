package org.ailab.wimfra.util.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午1:47
 * Desc:
 */
public class MultiValueBySet {
    private Object initValue;
    private Set valueSet;

    public MultiValueBySet(Object initValue) {
        this.initValue = initValue;
    }

    public MultiValueBySet(Object value1, Object value2) {
        addValue(value1);
        addValue(value2);
    }

    public void addValue(Object value) {
        if(initValue == null) {
            if(valueSet == null) {
                initValue = value;
            } else {
                valueSet.add(value);
            }
        } else {
            valueSet = new HashSet();

            valueSet.add(initValue);
            initValue = null;

            valueSet.add(value);
        }
    }

    public Object getValue() {
        if(initValue == null) {
            return valueSet;
        } else {
            return initValue;
        }
    }

    public Set toSet() {
        if(valueSet !=null) {
            return valueSet;
        } else {
            Set set = new HashSet(1);
            set.add(initValue);
            return set;
        }
    }
}
