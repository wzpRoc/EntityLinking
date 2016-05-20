package org.ailab.tem.wim.userGroupMenu;

import org.ailab.tem.wim.userGroupMenu.UserGroupMenu;
import org.ailab.tem.wim.userGroupMenu.UserGroupMenuBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组菜单业务逻辑
 */


public class UserGroupMenuDetailAction extends BaseDetailAction {
    public UserGroupMenuDetailAction() {
        dto = new UserGroupMenu();
        bl = new UserGroupMenuBL();
    }
}
