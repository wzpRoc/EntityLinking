package org.ailab.tem.wim.user;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;



/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-12
 * 功能描述：用户业务逻辑
 */
public class UserListAction extends BaseListAction {
    private UserBL bl;


    public UserListAction() {
        this.bl = new UserBL();
        super.bl = this.bl;
        this.condition = new UserListCondition();
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
        UserListCondition condition = (UserListCondition) this.condition;

        if (StringUtil.isEmpty(condition.userRole)) {
            condition.userRole = "";
        }

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

        if (StringUtil.isEmpty(condition.id)) {
            condition.id = "";
        }
    }

    public String getConditionString() {
        UserListCondition condition = (UserListCondition) this.condition;

        // 处理查询条件
        StringBuffer sb = new StringBuffer();
        sb.append(" 1=1 ");

        if(StringUtil.notEmpty(condition.userRole)) {
            sb.append(" AND role =").append(condition.userRole).append(" ");
        }

        if (StringUtil.notEmpty(condition.countryId)) {
            sb.append(" AND countryId=").append(condition.countryId).append(" ");
        }
        if (StringUtil.notEmpty(condition.category)) {
            sb.append(" AND category=").append(condition.category).append(" ");
        }

        if(StringUtil.notEmpty(condition.name)) {
            sb.append("AND userName LIKE '%").append(condition.name).append("%'");
        }
//        if (StringUtil.notEmpty(condition.name)) {
//            sb.append(" AND id IN (SELECT id from user where realname ");
//            if("true".equals(condition.fuzzyMatch)) {
//                sb.append(" LIKE '%").append(condition.name).append("%'");
//            } else {
//                sb.append(" = '").append(condition.name).append("'");
//            }
//            sb.append(")");
//        }
        if (StringUtil.notEmpty(condition.id)) {
            sb.append(" AND id IN (SELECT id from user where id ");
            if("true".equals(condition.fuzzyMatch)) {
                sb.append(" LIKE '%").append(condition.name).append("%'");
            } else {
                sb.append(" = '").append(condition.id).append("'");
            }
            sb.append(")");
        }
        return sb.toString();
    }

}
