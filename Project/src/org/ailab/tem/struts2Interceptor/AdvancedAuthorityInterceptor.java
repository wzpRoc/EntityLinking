package org.ailab.tem.struts2Interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.ailab.tem.wim.user.User;
import org.ailab.tem.wim.user.UserRole;
import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.user.BaseUser;

public class AdvancedAuthorityInterceptor extends AbstractAuthorityInterceptor{

	private static final long serialVersionUID = AdvancedAuthorityInterceptor.class.getName().hashCode();
	
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
			objAction.setBlMessage(new BLMessage(false, "您所在的用户组无法访问该页面！"));
			objAction.setRedirectUrl("../index.action");
			return MESSAGE;
		}
		return LOGIN;
	}

	@Override
	public boolean validateUserRole(BaseUser user) {
        final int role = ((User) user).getRole();
        if(role == UserRole.ADMINISTRATOR.getId()
                || role == UserRole.ANNOTATOR.getId()
                || role == UserRole.ANNOTATOR_ADMINISTRATOR.getId()
                ){
			return true;
		}
		return false;
	}

}
