package org.ailab.wimfra.webServer;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.user.BaseUser;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User: lutingming
 * Date: 13-11-24
 * Time: 下午4:04
 * Desc: 登录拦截器
 */
public class LoginCodeInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        //获取request
        HttpServletRequest request = ServletActionContext.getRequest();
        final String loginCode = request.getParameter("loginCode");
        if(StringUtil.notEmpty(loginCode)) {
            // 请求登录
            // 查找请求的session
            final HttpSession requestedSession = GlobalSessionMap.get(loginCode);
            if(requestedSession == null) {
                // 没有找到
                request.setAttribute("message", "您尚未登录或登录已超时");
                return "message";
            } else {
                // 找到
                BaseUser user = (BaseUser) requestedSession.getAttribute("user");
                if(user == null) {
                    // 没有用户对象
                    request.setAttribute("message", "您尚未登录");
                    return "message";
                } else {
                    // 找到用户对象
                    // 获取当前session
                    final HttpSession currentSession = request.getSession(true);
                    // 设置到当前session中
                    currentSession.setAttribute("user", user);
                    return invocation.invoke();
                }
            }
        }
        return invocation.invoke();
    }
}
