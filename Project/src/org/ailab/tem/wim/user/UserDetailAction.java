package org.ailab.tem.wim.user;

import org.ailab.tem.Constants;
import org.ailab.tem.wim.user.User;
import org.ailab.tem.wim.user.UserBL;
import org.ailab.wimfra.core.BaseDetailAction;
import org.ailab.wimfra.util.StringUtil;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-12
 * 功能描述：用户业务逻辑
 */


public class UserDetailAction extends BaseDetailAction {
    public UserDetailAction() {
        dto = new User();
        bl = new UserBL();
    }

    public String myExecute() throws Exception {
        // 取得当前用户
        User user = (User)getUser();
        if(user != null) {
            if(StringUtil.isEmpty(id)) {
                id = "" + user.getId();
            } else {
                if(user.getRole() == UserRole.ADMINISTRATOR.getId()) {
                    // ok
                } else {
                    // 其他用户，只能操作自己
                    id = "" + user.getId();
                }
            }
        } else {
            msg = "请登录";
            return LOGIN;
        }

        if(saveTag!=null && !saveTag.isEmpty()){
            User tmpUser = (User)dto;
            // 添加对user的处理, 普通用户
            if(!"adminUpdate".equals(dowhatInList)){
                User currentUser = (User)getUser();
                if(currentUser == null)
                    return "failure";
                // 更新session中的user
                currentUser.setUsername(tmpUser.getUsername());
                currentUser.setNicName(tmpUser.getNicName());
                currentUser.setRealName(tmpUser.getRealName());
                currentUser.setSex(tmpUser.getSex());
                currentUser.setDegree(tmpUser.getDegree());
                currentUser.setTelephone(tmpUser.getTelephone());
                currentUser.setMobilephone(tmpUser.getMobilephone());
                currentUser.setAddress(tmpUser.getAddress());
                currentUser.setEmail(tmpUser.getEmail());
                currentUser.setCarType(tmpUser.getCarType());
                currentUser.setCertificateType(tmpUser.getCertificateType());
                currentUser.setCertificateNo(tmpUser.getCertificateNo());
                currentUser.setHobby(tmpUser.getHobby());
                if(!"".equals(tmpUser.getPassword()))
                    currentUser.setPassword(tmpUser.getPassword());
                if("".equals(tmpUser.getCarPurchaseDate()))
                    currentUser.setCarPurchaseDate(null);
                else
                    currentUser.setCarPurchaseDate(tmpUser.getCarPurchaseDate());
                if("".equals(tmpUser.getBirthday()))
                    currentUser.setBirthday(null);
                else
                    currentUser.setBirthday(tmpUser.getBirthday());
                // 更新数据库
                dto = currentUser;
            }

            // 对空日期进行处理
            if("".equals(tmpUser.getBirthday()))
                ((User) dto).setBirthday(null);
            if("".equals(tmpUser.getCarPurchaseDate()))
                ((User) dto).setCarPurchaseDate(null);
            if("".equals(tmpUser.getLastLoginTime()))
                ((User) dto).setLastLoginTime(null);
            if("".equals(tmpUser.getRegisterTime()))
                ((User) dto).setRegisterTime(null);
            if("".equals(tmpUser.getSendValidationTime()))
                ((User) dto).setSendValidationTime(null);

        }

        if (StringUtil.isEmpty(saveTag)) {
            if ("add".equals(dowhat)) {
                bl.initDto(dto);
            } else {
                getDtoFromDB();
            }
        } else {
            saveDtoToDB();
        }

        return SUCCESS;
    }
}
