package org.ailab.tem.wim.user;

import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseDetailAction;
import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;

import java.util.List;


/**
 * 作	者:  OneChen
 * 创建日期: 2014-0-12
 * 功能描述：用户业务逻辑
 */


public class UserClientDetailAction extends BaseDetailAction {

    // 用户名
    private String userName;

    // 邮箱
    private String email;

    // 手机
    private String telephone;

    // 新密码
    private String newPassword;

    // 旧密码
    private String oldPassword;

    // 用户Id
    private String userId;

    public UserClientDetailAction() {
        dto = new User();
        bl = new UserBL();
    }

    public String myExecute() throws Exception {
        UserBL userBL = new UserBL();
        if("register".equals(dowhat)) {
            /**************************注册用户***************************/
            // 获取注册时间和用户登录时间
            String registerTime = TimeUtil.getYyyy_mm_dd_hh_mm_ss();
            String lastLoginTime = TimeUtil.getYyyy_mm_dd_hh_mm_ss();

            // 判断用户名的重复性
            List userList = userBL.getListByCondition("username = ?", userName);
            if(userList!= null && userList.size()!=0) {
                blMessage = new BLMessage(false, "用户名已被注册！");
            } else {
                // 插入新的用户信息
                User user = new User(userName, userName, userName, telephone, email, newPassword, registerTime, lastLoginTime);
                userBL.insert(user);
                blMessage = new BLMessage(true, "注册成功！", user);
            }
        } else if("update".equals(dowhat)) {
            /**************************修改用户***************************/
            // 获取原用户信息
            User user = (User) userBL.get(userId);
            if(user == null) {
                // 判断是否存在该用户
                blMessage = new BLMessage(false, "无该用户!");
            } else {
                // 判断用户原密码是否正确
               if(oldPassword.equals(user.getPassword())) {
                   // 判断新修改的用户名是否重复
                   List userList = userBL.getListByCondition("username = ? AND id <> ?", userName, userId);
                   if(userList!= null && userList.size()!=0) {
                       blMessage = new BLMessage(false, "用户名已被注册！");
                   } else {
                       // 更新用户信息
                       user.setUsername(userName);
                       user.setEmail(email);
                       user.setTelephone(telephone);
                       user.setPassword(newPassword);
                       userBL.update(user);
                       blMessage = new BLMessage(true, "修改成功！", user);
                   }
               } else {
                   blMessage = new BLMessage(false, "密码错误！");
               }
            }
        }


        return BL_MESSAGE_JSON;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

}
