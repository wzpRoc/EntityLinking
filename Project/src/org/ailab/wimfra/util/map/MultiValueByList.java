package org.ailab.wimfra.util.map;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午1:47
 * Desc:
 */
public class MultiValueByList {
    private Object initValue;
    private List valueList;

    public MultiValueByList(Object initValue) {
        this.initValue = initValue;
    }

    public MultiValueByList(Object value1, Object value2) {
        addValue(value1);
        addValue(value2);
    }

    public void addValue(Object value) {
        if(initValue == null) {
            if(valueList == null) {
                initValue = value;
            } else {
                valueList.add(value);
            }
        } else {
            valueList = new ArrayList();

            valueList.add(initValue);
            initValue = null;

            valueList.add(value);
        }
    }

    public Object getValue() {
        if(initValue == null) {
            return valueList;
        } else {
            return initValue;
        }
    }

    public List toList() {
        if(valueList!=null) {
            return valueList;
        } else {
            ArrayList list = new ArrayList();
            list.add(initValue);
            return list;
        }
    }
}
