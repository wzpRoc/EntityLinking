package org.ailab.entityLinking_old;

/**
 * User: Lu Tingming
 * Date: 15-5-16
 * Time: 下午6:26
 * Desc:
 */
public enum TSTag {
    UNKNOWN("UK"), TRADITIONAL("TT"), SIMPLIFIED("SS"), TRADITIONAL_TO_SIMPLIFIED("TS");

    private String value;

    TSTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
