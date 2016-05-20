package org.ailab.wimfra.core;

/**
 * User: Lu Tingming
 * Date: 13-3-29
 * Time: 下午3:45
 * Desc:
 */
public enum DBDataType {
    VARCHAR("varchar", -1),
    TEXT("text", 65536 - 2),
    LONG_TEXT("longText", 65536l * 65536l - 4);

    private String name;
    private long maxBytes;

    DBDataType(String name, long maxBytes) {
        this.name = name;
        this.maxBytes = maxBytes;
    }

    public String getName() {
        return name;
    }

    public long getMaxBytes() {
        return maxBytes;
    }
}
