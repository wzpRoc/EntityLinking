/**
 * 作者：彭程
 * 开发日期：2011-08-20
 * 功能：Struts2框架的基类。实现一些基本功能，如返回给页面的消息，当前用户信息，操作成功后跳转地址等
 * 最近修改日期：2011-09-29
 * 修改人：彭程
 * 修改内容：添加语种参数，区分用户选择的新闻/事件的语种
 */

package org.ailab.wimfra.core;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import flexjson.JSONSerializer;
import org.ailab.wimfra.user.BaseUser;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;
import org.apache.struts2.ServletActionContext;

import java.util.Map;

public abstract class BaseAction extends ActionSupport {
    public static final String SECTION = "section";
    public static final String ONE_DAY = "oneDay";
    //只保留日期按区段选择的功能
    public String onlySection = "false";
    // 日期模式
    protected String dateMode;

    private static final long serialVersionUID = -1;
    //日志工具
    protected Logger logger = Logger.getLogger(this.getClass());
    //Action返回的状态位
    protected static String MESSAGE = "message";
    //Action返回的状态位
    protected static String BL_MESSAGE_JSON = "blMessageJson";
    //返回结果的包装类
    protected BLMessage blMessage;
    //执行完后页面跳转的目标地址，默认为后退
    protected String redirectUrl = "javascript:history.go(-1);";
    //页面跳转的方式如在当前框架内打开，在父框架打开等 self/top
    protected String target = "self";
    //删除多个对象的Id参数，如:20,35,30...
    protected String opIds = "";

    //当前传递的日期
    protected String date;

    //开始日期结束日期
    protected String startDate;
    protected String endDate;

    //用户选择的语种
    protected String lang;
    // 是否需要右侧高级选项的标志位。添加人：playcoin 2012-08-07
    protected boolean rightAdOptionOn = true;

    // added by lutm 20120615
    // 高亮关键词
    protected String highlighterKeywords;
    protected String methodType;
    protected String pageTitle;
    protected String debugMode;
    protected String msg;
    protected String actionName;

    protected boolean needOuterPartByParameter = true;


    public String execute() throws Exception {
        try {
            actionName = getActionNameFromServletPath();
            return myExecute();
        } catch (Exception e) {
            logger.error(e);
            this.msg = e.toString();
            return SUCCESS;
        }
    }

    protected String getActionNameFromServletPath() {
        String temp;

        final String servletPath = ServletActionContext.getRequest().getServletPath();
        int start = servletPath.lastIndexOf('/');
        int idxOfQuestionMark = servletPath.indexOf('?', start);
        if(idxOfQuestionMark < 0) {
            temp = servletPath.substring(start);
        } else {
            temp = servletPath.substring(start, idxOfQuestionMark);
        }

        if(temp.startsWith("/")) {
            temp = temp.substring(1);
        }
        return temp;
    }


    public abstract String myExecute() throws Exception;


    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setOpIds(String opIds) {
        this.opIds = opIds;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the blMessage
     */
    public BLMessage getBlMessage() {
        return blMessage;
    }

    /**
     * @param blMessage the blMessage to set
     */
    public void setBlMessage(BLMessage blMessage) {
        this.blMessage = blMessage;
    }

    /**
     * 日期格式yyyymmdd
     *
     * @return the pubdate
     */
    public String getDate() {
        return date;
    }

    /**
     * 日期格式yyyy-mm-dd
     *
     * @return the formatDate
     */
    public String getFormatDate() {
        if (date != null && date.length() == 8)
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);

        return null;
    }

    /**
     * @param date the pubdate to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the user
     */
    public BaseUser getUser() {
        final Map<String, Object> session = getSession();
        if (session == null) {
            return null;
        }

        return (BaseUser) session.get("user");
    }

    /**
     * @return the user
     */
    public int getUserId() {
        BaseUser user = getUser();
        if(user == null) {
            return 0;
        } else {
            return user.getId();
        }
    }

    /**
     * @param user the user to set
     */
    public void setUser(BaseUser user) {
        final Map<String, Object> session = getSession();
        if (session == null) {
            return;
        } else {
            session.put("user", user);
        }

    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    public String getStartDateWithHyphen() {
        return TimeUtil.addHyphen(startDate);
    }

    public String getEndDateWithHyphen() {
        return TimeUtil.addHyphen(endDate);
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 日期格式yyyy-mm-dd
     *
     * @return the formatDate
     */
    public String getFormatStartDate() {
        if (startDate != null) {
            startDate = startDate.replace("-", "");
            if (startDate.length() == 8)
                return startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6);
        }

        return null;
    }

    /**
     * 日期格式yyyy-mm-dd
     *
     * @return the formatDate
     */
    public String getFormatEndDate() {
        if (endDate != null) {
            endDate = endDate.replace("-", "");
            if (endDate.length() == 8)
                return endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6);
        }

        return null;
    }

    public String getHighlighterKeywords() {
        return highlighterKeywords;
    }

    public void setHighlighterKeywords(String highlighterKeywords) {
        this.highlighterKeywords = highlighterKeywords;
    }

    public String getDateMode() {
        return dateMode;
    }

    public void setDateMode(String dateMode) {
        this.dateMode = dateMode;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getPageTitle() throws Exception {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public boolean isRightAdOptionOn() {
        return rightAdOptionOn;
    }

    public void setRightAdOptionOn(boolean rightAdOptionOn) {
        this.rightAdOptionOn = rightAdOptionOn;
    }

    public String getActionName() {
        if(actionName == null) {
            actionName = getActionNameFromServletPath();
        }
        return actionName;
    }

    public String getLanguageId() {
        return lang;
    }

    public void setLanguageId(String languageId) {
        this.lang = languageId;
    }

    public String getLeftBox() {
        Map<String, Object> session = getSession();
        if (session == null) {
            return null;
        } else {
            return (String) session.get("leftBox");
        }
    }


    /**
     * 获得会话
     */
    protected Map<String, Object> getSession() {
        ActionContext ctx = ActionContext.getContext();
        return ctx.getSession();
    }

    public void setLeftBox(String leftBox) {
        Map<String, Object> session = getSession();
        if (session == null) {
            return;
        } else {
            session.put("leftBox", leftBox);
        }
    }


    public String getOnlySection() {
        return onlySection;
    }

    public void setOnlySection(String onlySection) {
        this.onlySection = onlySection;
    }


    /**
     * 只支持区段时间
     */
    protected void enableOnlySection() {
        dateMode = SECTION;
        onlySection = "true";
    }


    /**
     * 得到最后查询地址
     */
    public String getLastListLocation() {
        final Map<String, Object> session = getSession();
        if (session == null) {
            return null;
        }

        return (String) session.get(BaseListAction.LAST_LIST_LOCATION);
    }

    public String getDebugMode() {
        return debugMode;
    }

    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugModeBoolean() {
        return "1".equals(debugMode) || "true".equalsIgnoreCase(debugMode);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * @return
     */
    public String getBlMessageJson() {
        JSONSerializer serializer = new JSONSerializer();
        String json = serializer.exclude("*.class").serialize(blMessage);
        return json;
    }



    public boolean isNeedOuterPart() {
        if(!needOuterPartByParameter) {
            return false;
        }
        String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
        if (userAgent != null) {
            return !userAgent.contains(".NET") && !userAgent.contains("TRUE-E");
        }

        return true;
    }

    public void setNeedOuterPartByParameter(boolean needOuterPartByParameter) {
        this.needOuterPartByParameter = needOuterPartByParameter;
    }

    public String getCtx() {
        return ServletActionContext.getRequest().getContextPath();
    }
}
