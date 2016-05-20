package org.ailab.tem.wim.userGroupUser;

import org.ailab.tem.wim.userGroupUser.UserGroupUser;
import org.ailab.tem.wim.userGroupUser.UserGroupUserBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组成员业务逻辑
 */


public class UserGroupUserDetailAction extends BaseDetailAction {
    public UserGroupUserDetailAction() {
        dto = new UserGroupUser();
        bl = new UserGroupUserBL();
    }
}
