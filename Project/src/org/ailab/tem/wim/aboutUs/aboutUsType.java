package org.ailab.tem.wim.aboutUs;

/**
 * Created with IntelliJ IDEA.
 * User: OneChen
 * Date: 13-11-13
 * Time: 下午4:26
 * Desc: The type of aboutUs
 */
public enum aboutUsType {
    INTRODUCTION(1, "企业介绍"),
    FEATURE(2, "企业特点"),
    CULTURE(3, "企业文化"),
    DEVELOPMENT(4, "发展历程"),
    DEPARTMENT(5, "分支机构")
    ;

    // ID
    protected int id;

    // 名称
    protected String name;

    // 构造函数
    aboutUsType(int id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * 得到ID
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * 得到名称
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 通过ID获得名称
     * @param id
     * @return
     */
    public static aboutUsType getByValue(int id) {
        for(aboutUsType obj : aboutUsType.values()) {
            if(obj.getId() == id) {
                return obj;
            }
        }

        return null;
    }
}
