package org.ailab.tem.wim.agentInfo;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;


/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：代理商业务逻辑 ListAction
 */
public class AgentInfoListAction extends BaseListAction {

    // 代理商业务逻辑
    private AgentInfoBL bl;

    /**
     * 构造函数
     */
    public AgentInfoListAction() {
        this.bl = new AgentInfoBL();
        super.bl = this.bl;
        this.condition = new AgentInfoListCondition();
    }


    /**
     * 根据查询条件得到列表
     *
     * @throws Exception
     */
    public String myExecute() throws Exception {
        super.myExecute();
        page.setWhereCondition(getConditionString());
//        page.setOrderBy(" nrOccur DESC");
        bl.getList(page);

        return SUCCESS;
    }


    /**
     * 初始查询条件检查
     * 目前状态：暂未使用
     */
    public void checkCondition() {
        AgentInfoListCondition condition = (AgentInfoListCondition) this.condition;

        if (StringUtil.isEmpty(condition.countryId)) {
            condition.countryId = "";
        }

        if (StringUtil.isEmpty(condition.category)) {
            condition.category = "";
        }

        if (StringUtil.isEmpty(condition.fuzzyMatch )) {
            condition.fuzzyMatch = "";
        }

        if (StringUtil.isEmpty(condition.name)) {
            condition.name = "";
        }
    }

    /**
     * 查询条件确定
     * 目前状态：暂未使用
     * @return
     */
    public String getConditionString() {
        AgentInfoListCondition condition = (AgentInfoListCondition) this.condition;

        // 处理查询条件
        StringBuffer sb = new StringBuffer();
        sb.append(" 1=1 ");

        if (StringUtil.notEmpty(condition.countryId)) {
            sb.append(" AND countryId=").append(condition.countryId).append(" ");
        }
        if (StringUtil.notEmpty(condition.category)) {
            sb.append(" AND category=").append(condition.category).append(" ");
        }
        if (StringUtil.notEmpty(condition.name)) {
            sb.append("AND name LIKE '%").append(condition.name).append("%'");
        }
        return sb.toString();
    }

}
