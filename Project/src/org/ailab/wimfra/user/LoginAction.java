package org.ailab.wimfra.user;

import org.ailab.tem.wim.user.User;
import org.ailab.tem.wim.user.UserBL;
import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseAction;
import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-12
 * 功能描述：用户登录action
 */
public class LoginAction extends BaseAction {
    protected String username;
    protected String password;

    public LoginAction() {
    }


    /**
     * 根据查询条件得到列表
     *
     * @throws Exception
     */
    public String myExecute() throws Exception {
        if (StringUtil.isEmpty(username)) {
            blMessage = new BLMessage(false, "用户名为空");
            return LOGIN;
        } else if (StringUtil.isEmpty(password)) {
            blMessage = new BLMessage(false, "密码为空");
            return LOGIN;
        } else {
            blMessage = new BLMessage(false, "登录异常");
            UserBL bl = new UserBL();
            User user = bl.get(username, password);

            if (user == null) {
                blMessage = new BLMessage(false, "登录失败");
            } else {
                blMessage = new BLMessage(true, "登录成功", user);
            }
            setUser(user);
            return "admin";
        }
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
