package org.ailab.wimfra.user;

import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-12
 * 功能描述：用户登出action
 */
public class LogoutAction extends BaseAction {
    public String myExecute() throws Exception {
        try {
            setUser(null);
            blMessage = new BLMessage(true, "已注销", null);
        } catch (Exception e) {
            blMessage = new BLMessage(false, "注销失败");
            e.printStackTrace();
        }

        return SUCCESS;
    }
}
