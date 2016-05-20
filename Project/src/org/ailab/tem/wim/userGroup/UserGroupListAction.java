package org.ailab.tem.wim.userGroup;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组业务逻辑
 */
public class UserGroupListAction extends BaseListAction {
    private UserGroupBL bl;


    public UserGroupListAction() {
        this.bl = new UserGroupBL();
        super.bl = this.bl;
        this.condition = new UserGroupListCondition();
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


    public void checkCondition() {
        UserGroupListCondition condition = (UserGroupListCondition) this.condition;

        if (StringUtil.isEmpty(condition.countryId)) {
            condition.countryId = "";
        }

        if (StringUtil.isEmpty(condition.category)) {
            condition.category = "";
        }

        if (StringUtil.isEmpty(condition.fuzzyMatch)) {
            condition.fuzzyMatch = "";
        }

        if (StringUtil.isEmpty(condition.name)) {
            condition.name = "";
        }
    }

    public String getConditionString() {
        UserGroupListCondition condition = (UserGroupListCondition) this.condition;

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
            sb.append(" AND name LIKE '%").append(condition.name).append("%' ");
        }
        return sb.toString();
    }

}
