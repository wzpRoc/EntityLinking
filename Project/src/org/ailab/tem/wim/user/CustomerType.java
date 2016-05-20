package org.ailab.tem.wim.user;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-11-13
 * Time: 下午4:26
 * Desc: 客户类别
 */
public enum CustomerType {
    POTENTIAL_CUSTOMER(0, "潜在客户"),
    DEVICE_CUSTOMER(1, "设备客户"),
    DATA_CUSTOMER(2, "数据客户")
    ;

    protected int id;
    protected String name;

    CustomerType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CustomerType getByValue(int id) {
        for(CustomerType obj : CustomerType.values()) {
            if(obj.getId() == id) {
                return obj;
            }
        }

        return null;
    }
}
