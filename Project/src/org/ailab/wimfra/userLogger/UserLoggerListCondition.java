package org.ailab.wimfra.userLogger;


import org.ailab.wimfra.core.ListCondition;

/**
 * User: ZhangQiang
 * Date: 13-11-24
 * Time: 下午9:48
 * Desc:
 */
public class UserLoggerListCondition extends ListCondition {
    // 用户名
    protected String userId;

    // 时间
    protected String dateBegin;
    protected String dateEnd;

    // 类名（操作名称）
    protected String className;

    // 方法名
    protected String method;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
