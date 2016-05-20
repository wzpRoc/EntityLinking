package org.ailab.tem.wim.base;

import java.lang.String;

/**
 * Created with IntelliJ IDEA.
 * User: onechen
 * Date: 13-11-29
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public enum PropertyStateType {

    NOTANNOTATED(0, "未标注"),
    ANNOTATED(1, "已标注"),
    PASSCHECK(2,"审核通过"),
    FAILCHECK(-1,"审核不通过")
    ;


    protected int id;
    protected String name;

    PropertyStateType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static PropertyStateType getByValue(int id) {
        for(PropertyStateType obj : PropertyStateType.values()) {
            if(obj.getId() == id) {
                return obj;
            }
        }
        return null;
    }
}
