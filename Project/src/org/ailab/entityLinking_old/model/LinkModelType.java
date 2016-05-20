package org.ailab.entityLinking_old.model;

/**
 * User: Lu Tingming
 * Date: 15-5-31
 * Time: 下午10:22
 * Desc:
 */
public enum LinkModelType {
    mentionEntity("mention-entity"),
    docGlobal("doc-global")
    ;

    public String name;

    private LinkModelType(String name) {
        this.name = name;
    }

}
