/**
 * 作者：彭程
 * 开发日期：2011-10-13
 * 系统所用的认证拦截器，继承自抽象类AbstractAuthorityInterceptor
 */
package org.ailab.tem.struts2Interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import org.ailab.wimfra.user.BaseUser;

public class AuthorityInterceptor extends AbstractAuthorityInterceptor{

	private static final long serialVersionUID = AuthorityInterceptor.class.getName().hashCode();
	// 指定action返回的结果名
	private static final String LOGIN = "login";
	
	@Override
	public String validate(ActionInvocation invocation) throws Exception {
		// 普通用户，直接继续
		return invocation.invoke();
	}

	@Override
	public boolean validateUserRole(BaseUser user) {
		// TODO Auto-generated method stub
		return true;
	}

}
