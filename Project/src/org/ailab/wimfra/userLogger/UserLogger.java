package org.ailab.wimfra.userLogger;


import org.ailab.wimfra.core.BaseDTO;
import org.ailab.wimfra.util.StringUtil;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-25
 * 功能描述：用户日志
 */
public class UserLogger extends BaseDTO {
    // id
    protected int id;

    // 用户ID
    protected int userId;

    // 级别
    protected int logLevel;

    // 操作日期
    protected String opDate;

    // 开始时间
    protected String startTime;

    // 持续时长
    protected int duration;

    // 类名
    protected String className;

    // 方法名
    protected String method;

    // 操作名称
    protected String opName;

    // 结果
    protected int result;

    // 地址
    protected String ip;

    // url
    protected String url;

    public UserLogger() {
    }

    public UserLogger(int userId, String opDate, String startTime, String className, String opName, String method,  int result, String ip, String url) {
        this.userId = userId;
        this.opDate = opDate;
        this.startTime = startTime;
        this.className = className;
        this.opName = opName;
        this.method = method;
        this.result = result;
        this.ip = ip;
        this.url = url;
    }

    /**
     * 得到id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 得到用户ID
     *
     * @return 用户ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserRealName() throws NoSuchFieldException, IllegalAccessException {
        // return UserBL.getRealNameFromCache(userId);
        return null;
    }

    public String getUserName() throws NoSuchFieldException, IllegalAccessException {
        // return UserBL.getRealNameFromCache(userId);
        return null;
    }

    /**
     * 得到级别
     *
     * @return 级别
     */
    public int getLogLevel() {
        return logLevel;
    }

    /**
     * 设置级别
     *
     * @param logLevel 级别
     */
    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * 得到操作日期
     *
     * @return 操作日期
     */
    public String getOpDate() {
        return opDate;
    }

    /**
     * 设置操作日期
     *
     * @param opDate 操作日期
     */
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    /**
     * 得到开始时间
     *
     * @return 开始时间
     */
    public String getStartTime() {
        if (StringUtil.notEmpty(startTime) && startTime.length() > 20) {
            startTime = startTime.substring(0, 19);
        }
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 得到持续时长
     *
     * @return 持续时长
     */
    public int getDuration() {
        return duration;
    }

    /**
     * 设置持续时长
     *
     * @param duration 持续时长
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 得到类名
     *
     * @return 类名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置类名
     *
     * @param className 类名
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 得到方法名
     *
     * @return 方法名
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置方法名
     *
     * @param method 方法名
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 得到操作名称
     *
     * @return 操作名称
     */
    public String getOpName() {
        return opName;
    }

    /**
     * 设置操作名称
     *
     * @param opName 操作名称
     */
    public void setOpName(String opName) {
        this.opName = opName;
    }

    /**
     * 得到结果
     *
     * @return 结果
     */
    public int getResult() {
        return result;
    }

    public String getResultDes() {
        return result == 1 ? "成功" : "失败";
    }

    /**
     * 设置结果
     *
     * @param result 结果
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * 得到地址
     *
     * @return 地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置地址
     *
     * @param ip 地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 得到url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
