package org.ailab.tem.wim.user;

import java.lang.String;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-11-13
 * Time: 下午4:26
 * Desc: 用户角色
 */
public enum UserRole {
    GUEST(0, "游客"),
    GENERAL(1, "普通用户"),
    ANNOTATOR(11, "标注用户"),
    ANNOTATOR_ADMINISTRATOR(12, "标注管理员"),
    ADMINISTRATOR(100, "管理员")
    ;

    protected int id;
    protected String name;

    UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static UserRole getByValue(int id) {
        for(UserRole obj : UserRole.values()) {
            if(obj.getId() == id) {
                return obj;
            }
        }

        return null;
    }

}
