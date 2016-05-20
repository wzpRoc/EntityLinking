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
public class TankGroupMemberAuthorityInterceptor extends AbstractAuthorityInterceptor{

	private static final long serialVersionUID = TankGroupMemberAuthorityInterceptor.class.getName().hashCode();
	
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
			objAction.getBlMessage().setMessage("您不是智库工作组的成员或者超级管理员，无法访问该页面！");
			return MESSAGE;
		}
		return LOGIN;
	}

	@Override
	public boolean validateUserRole(BaseUser user) {
		if(user == null)
			return false;
		
//		// 在这个类中，该方法用于判定是否是智库组的成员
//        BaseUser memUser = UserUtil.getUserById(user.getId());
//
//		if(memUser.inGroup(TankAction.TANKGROUPID)>0 || memUser.getRole() == UserUtil.ROLE_ADMIN){
//			return true;
//		}
		return false;
	}

}
