package org.ailab.tem.struts2Interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.user.BaseUser;

/**
 * 管理员页面的拦截器
 * 主要用于用户角色的认证
 * 开发人：彭程
 * 开发日期：2011-10-20
 * @author playcoin
 *
 */
public class AdminAuthorityInterceptor extends AbstractAuthorityInterceptor{

	private static final long serialVersionUID = AdminAuthorityInterceptor.class.getName().hashCode();
	
	private static final String MESSAGE = "message";
	
	// 指定action返回的结果名
	private static final String LOGIN = "login";
	
	@Override
	public String validate(ActionInvocation invocation) {
		//Struts2执行上下文
		ActionContext ctx = invocation.getInvocationContext();
		//获取当前要执行的Action
        BaseAction objAction = (BaseAction)invocation.getAction();
        BaseUser user = objAction.getUser();
		
		if((null != user) && !validateUserRole(user)){
			objAction.getBlMessage().setMessage("您所在的用户组无法访问该页面！");
			objAction.setRedirectUrl("../index.action");
			return MESSAGE;
		}
		return LOGIN;
	}

	@Override
	public boolean validateUserRole(BaseUser user) {
//		if(user.getRole() == UserUtil.ROLE_ADMIN){
//			return true;
//		}
		return false;
	}

}
