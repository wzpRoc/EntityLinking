package org.ailab.tem.wim.agentInfo;

import org.ailab.tem.wim.agentInfo.AgentInfo;
import org.ailab.tem.wim.agentInfo.AgentInfoBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：代理商业务逻辑 DetailAction
 */

public class AgentInfoDetailAction extends BaseDetailAction {
    /**
     * 构造函数
     */
    public AgentInfoDetailAction() {
        dto = new AgentInfo();
        bl = new AgentInfoBL();
    }
}
