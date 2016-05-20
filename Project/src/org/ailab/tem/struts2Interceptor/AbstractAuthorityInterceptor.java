/**作者：彭程
 * 开发日期：2011-10-13
 * Struts2框架权限检查拦截器
 * 所有经由Struts2框架处理的请求都会启动该拦截器以检查用户权限
 */
package org.ailab.tem.struts2Interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.ailab.tem.wim.user.User;
import org.ailab.tem.wim.user.UserBL;
import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.user.BaseUser;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class AbstractAuthorityInterceptor extends AbstractInterceptor
{
	//序列化ID
	private static final long serialVersionUID = AbstractAuthorityInterceptor.class.getName().hashCode();
	
	//用户需要输入用户名、密码的标志
	protected static final String INPUT = "input";
	//跳转到消息页面
	protected static final String MESSAGE = "message";
    protected BaseUser user;

    public String intercept(ActionInvocation invocation) throws Exception
	{
		//用户登录成功的标志位
		boolean flag = false;
		//Struts2执行上下文
		ActionContext ctx = invocation.getInvocationContext();
		//获取当前Session
		Map<String,Object> session = ctx.getSession();
		//获取当前要执行的Action
		BaseAction objAction = (BaseAction)invocation.getAction();
        user = objAction.getUser();
		
		if((null != session) && (null != session.get("user")))
		{//已经登录
			flag = true;
			//取session中user值
			user = (BaseUser)session.get("user");
		}
		else
		{//如果没有登录
			//取得客户端发送到服务器端的http请求参数
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			// 取得客户端发送到服务器端的Cookie
			Cookie[] cookies = request.getCookies();
			// 如果Cookie不为空
			if (cookies != null) {
				// 声明user对象并用Cookie中的用户名密码，填充
                BaseUser userFromCookie = new BaseUser();
				for (Cookie c : cookies) {
					if (c.getName().equals("username")) {
						userFromCookie.setUsername(c.getValue());
					}
					if (c.getName().equals("password")) {
						// Cookie中的密码未加密
						userFromCookie.setPassword(c.getValue());
					}
				}
				
				// 用户验证工具类
				// UserBL userBl = new UserBL();
				// BLMessage blMessage = userBl.login(userFromCookie);
                // 从内存中检测用户名密码，主要是为了数据一致，因为用户信息存放在内存中
                BLMessage blMessage = (new UserBL()).login(userFromCookie);

				if (blMessage.isSuccess()) {//验证通过
					//讲当前用户信息保存到session
					session.put("user", blMessage.getData());
					//为user赋值，用于设置action中的user对象
					user = (BaseUser) blMessage.getData();
					//登录成功
					flag = true;
				}
			}
			
		}
		
		if(flag)
		{//登录成功
			//设置当前Action的用户信息
			objAction.setUser((User) user);
			// 再进行用户角色认证
			if(validateUserRole(user)){
				//如果成功，进入Struts2的下一步处理
				return invocation.invoke();
			}
		}
		//如果登录不成功，进行下一步处理
		return validate(invocation);
    }
	
	//如果登录不成功，进行下一步处理,根据不同的验证方式，进行不同的处理
	public abstract String validate(ActionInvocation invocation) throws Exception;
	
	//用户角色认证
	public abstract boolean validateUserRole(BaseUser user);
}
