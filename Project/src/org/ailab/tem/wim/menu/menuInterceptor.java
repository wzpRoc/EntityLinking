package org.ailab.tem.wim.menu;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.ailab.tem.wim.user.User;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.user.BaseUser;

/**
 * Author: ZhangQiang
 * Date: 14-2-28
 * Time: 下午6:50
 * Desc: 用户菜单拦截器 生成后台管理员菜单
 */
public class menuInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        //获取当前执行的action
        BaseAction action = (BaseAction) invocation.getAction();
        BaseUser user= action.getUser();
        if(user == null){
            //todo 重定向到登陆界面
        }



        return null;
    }
}
