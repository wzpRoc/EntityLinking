package org.ailab.wimfra.userLogger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.user.BaseUser;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * User: ZhangQiang
 * Date: 13-11-24
 * Time: 下午4:04
 * Desc:  用户日志拦截器
 */
public class LoggerInterceptor extends AbstractInterceptor {
    private static Logger logger = Logger.getLogger(LoggerInterceptor.class);


    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        // determine whether or not this is a heart-beat message
        if("/heartBeat".equals(request.getServletPath())) {
            // heart-beat message should not be included
            return invocation.invoke();
        }

        //获取当前执行的action
        BaseAction action = (BaseAction) invocation.getAction();
        String actionName = action.getActionName();
        String opName;
        if (StringUtil.notEmpty(action.getPageTitle())) {
            opName = action.getPageTitle();
        } else {
            opName = actionName;
        }

        // 获取方法名和执行结果
        String method = invocation.getProxy().getMethod();
        if (method.equals("execute")||method.equals("list")) {
            method = "查询";
        } else if (method.equals("add")) {
            method = "新增";
        } else if (method.equals("edit")) {
            method = "编辑";
        } else if (method.equals("delete")) {
            method = "删除";
        }
        boolean isSuccess = invocation.getProxy().getExecuteResult();
        int result = isSuccess ? 1 : 0;

        //获取执行时间和用户
        String startTime = TimeUtil.getDateTimeWithFormat();
        String opDate= TimeUtil.getYyyy_mm_dd();
        BaseUser user = action.getUser();

        int userId;
        final String username;
        if(user!=null) {
            userId = user.getId();
            username = user.getUsername();
        } else {
            userId = 0;
            username = "[未登录]";
        }

        //获取IP地址和url
        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        if (StringUtil.notEmpty(url) && url.length() > 1023) {
            url = url.substring(0, 1022);
        }

        //将数据插入数据库
        logger.debug("ip:" + ip + "的用户" + username + "在" + startTime + "在" + opName + "页面进行了" + method + "操作，结果：" + result);

        UserLogger userLogger = new UserLogger(userId,opDate,startTime, actionName, opName, method, result, ip, url);
        (new UserLoggerBL()).insert(userLogger);

        return invocation.invoke();
    }
}
