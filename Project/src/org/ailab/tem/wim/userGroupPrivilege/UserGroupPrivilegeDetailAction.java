package org.ailab.tem.wim.userGroupPrivilege;

import org.ailab.tem.wim.userGroupPrivilege.UserGroupPrivilege;
import org.ailab.tem.wim.userGroupPrivilege.UserGroupPrivilegeBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-06-21
 * 功能描述：用户组权限表业务逻辑
 */


public class UserGroupPrivilegeDetailAction extends BaseDetailAction {
    public UserGroupPrivilegeDetailAction() {
        dto = new UserGroupPrivilege();
        bl = new UserGroupPrivilegeBL();
    }
}
