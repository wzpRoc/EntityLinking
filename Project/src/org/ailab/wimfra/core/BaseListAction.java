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
import org.ailab.wimfra.user.BaseUser;
import org.ailab.wimfra.util.Config;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseListAction extends BaseAction {
    private static final long serialVersionUID = -1;
    protected static final String LAST_LIST_LOCATION = "LAST_LIST_LOCATION";
    //日志工具
    protected Logger logger = Logger.getLogger(BaseListAction.class);
    // 用于翻页的页码
    protected Page page = new Page(20);
    protected ListCondition condition;
    protected String ids;
    protected String clearSession;
    protected String enableSession;
    protected String submitTag;
    protected String dowhat;
    protected String tempDowhat;
    protected List<ClusterList> rightClusterLists;
    protected BaseBL bl;


    public String myExecute() throws Exception {
        ActionContext ctx = ActionContext.getContext();
        BaseUser user = (BaseUser) getUser();

        // 处理tempDowhat
        handleTempDowhat();

        // 是否从会话中清空条件
        if ("1".equals(clearSession) || "true".equalsIgnoreCase(clearSession)) {
            clearConditionFromSession();
        }

        // 是否启用会话
        if (!"1".equals(enableSession) && !"true".equalsIgnoreCase(enableSession)) {
            // do nothing
        } else {
            // 是否是提交的查询
            if (submitTag == null) {
                // 不是用户提交的
                // 尝试获得会话中的条件
                ListCondition conditionInSession = getConditionFromSession();
                if (conditionInSession == null) {
                    // 会话为空
                    // 在session中保存查询条件
                    saveConditionToSession();
                } else {
                    // 使用会话中的对象
                    setCondition(conditionInSession);
                }
            } else {
                // 用户提交
                saveConditionToSession();
            }
        }

        // 检查查询条件
        checkCondition();

        return SUCCESS;
    }

    public void handleDate() {
        if (StringUtil.isEmpty(dateMode)) {
            dateMode = "section";
        }

        if (StringUtil.isEmpty(endDate)) {
            // 设置默认日期
            if(Config.demoMode && StringUtil.notEmpty(Config.currentDate)) {
                endDate = Config.currentDate;
            } else {
                endDate = TimeUtil.getYyyy_mm_dd();
            }
        }
        if (StringUtil.isEmpty(startDate)) {
            if (ONE_DAY.equals(dateMode)) {
                startDate = endDate;
            } else {
                // 设置默认日期
                startDate = TimeUtil.getDateBefore(endDate, 7);
            }
        }
    }

    public ListCondition getConditionFromSession() {
        if(condition == null) {
            return null;
        }
        ActionContext ctx = ActionContext.getContext();
        return (ListCondition) ctx.getSession().get(condition.getClass().getName());
    }

    public void saveConditionToSession() {
        ActionContext ctx = ActionContext.getContext();
        ctx.getSession().put(condition.getClass().getName(), condition);
    }


    public void clearConditionFromSession() {
        ActionContext ctx = ActionContext.getContext();
        final Map<String, Object> session = ctx.getSession();
        if(session == null) {
            return;
        }

        session.remove(getCondition().getClass().getName());
        session.remove(LAST_LIST_LOCATION);
    }


    public void checkCondition() throws Exception {

    }

    public String getConditionString() throws Exception {
        return null;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ListCondition getCondition() {
        return this.condition;
    }

    public void setCondition(ListCondition condition) {
        this.condition = condition;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getClearSession() {
        return clearSession;
    }

    public void setClearSession(String clearSession) {
        this.clearSession = clearSession;
    }

    public String getEnableSession() {
        return enableSession;
    }

    public void setEnableSession(String enableSession) {
        this.enableSession = enableSession;
    }

    public String getSubmitTag() {
        return submitTag;
    }

    public void setSubmitTag(String submitTag) {
        this.submitTag = submitTag;
    }

    public String getModuleName() {
        String actionName = getActionName();
        return actionName.substring(0, actionName.length() - 4);
    }

    public String getDowhat() {
        return dowhat;
    }

    public void setDowhat(String dowhat) {
        this.dowhat = dowhat;
    }

    public String getDowhatTitle() {
        if ("annotation".equals(dowhat)) {
            return "标注";
        } if("check".equals(dowhat)){
            return "审核";
        } else {
            return "查询";
        }
    }

    public String getModuleTitle() {
        return getModuleName();
    }

    public String getPageTitle() {
        if (StringUtil.notEmpty(pageTitle)) {
            return pageTitle;
        } else {
            final String moduleTitle = getModuleTitle();
            final String dowhatTitle = getDowhatTitle();
            if (moduleTitle.endsWith(dowhatTitle)) {
                return moduleTitle;
            } else {
                return moduleTitle + dowhatTitle;
            }
        }
    }

    public List<ClusterList> getRightClusterLists() {
        return rightClusterLists;
    }

    public void setRightClusterLists(List<ClusterList> rightClusterLists) {
        this.rightClusterLists = rightClusterLists;
    }

    public void addClusterList(ClusterList clusterList) {
        if (rightClusterLists == null) {
            rightClusterLists = new ArrayList<ClusterList>();
        }
        rightClusterLists.add(clusterList);
    }

    public List getList() {
        return ((Page) blMessage.data).getList();
    }

    public String getTempDowhat() {
        return tempDowhat;
    }

    public void setTempDowhat(String tempDowhat) {
        this.tempDowhat = tempDowhat;
    }

    public void handleTempDowhat() throws Exception {
        if ("deleteFromList".equals(tempDowhat)) {
            BaseUser user = (BaseUser) getUser();
            if(user==null || !user.isPermittedToDelete()){
                throw new Exception("当前用户无权限执行删除操作");
            }
            deleteFromList();
        }
    }

    public int deleteFromList() throws Exception {
        return bl.delete(ids);
    }


    /**
     * 设置最近查询地址
     */
    public void setLocation(String location) {
        if(StringUtil.isEmpty(location)) {
            return;
        }

        final Map<String,Object> session = getSession();
        if(session == null) {
            return;
        }

        session.put(LAST_LIST_LOCATION, location);
    }


}
