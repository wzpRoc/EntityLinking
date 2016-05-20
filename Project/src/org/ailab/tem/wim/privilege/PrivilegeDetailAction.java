package org.ailab.tem.wim.privilege;

import org.ailab.tem.wim.privilege.Privilege;
import org.ailab.tem.wim.privilege.PrivilegeBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-06-21
 * 功能描述：权限表业务逻辑
 */


public class PrivilegeDetailAction extends BaseDetailAction {
    public PrivilegeDetailAction() {
        dto = new Privilege();
        bl = new PrivilegeBL();
    }
}
