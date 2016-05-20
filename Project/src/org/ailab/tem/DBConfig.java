package org.ailab.tem;

/**
 * User: Lu Tingming
 * Date: 15-5-7
 * Time: 下午2:20
 * Desc:
 */
public enum DBConfig {
    defaultDB(null),
    wikiDB("wzp/wiki"),
    docDB("doc"),
    adminDB("admin");

    private String name;

    private DBConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
