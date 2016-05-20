package org.ailab.wimfra.core;

import flexjson.JSONSerializer;

/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:24:14
 * Desc: base class of data transfer object
 * implemented by RuleSet
 */
public class BaseDTO {
    public int getId() {
        return Integer.MIN_VALUE;
    }


    public void setId(int i) {
        // do nothing
    }


    public String toJson() {
        return (new JSONSerializer()).exclude("class").serialize(this);
    }
}
