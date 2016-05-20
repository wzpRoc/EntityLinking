package %packagePrefix%.%tableName%;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;



/**
 * 作	者: %user%
 * 创建日期: %date%
 * 功能描述：%tableComment%业务逻辑
 */
public class %tableName_upInitial%ListAction extends BaseListAction {
    private %tableName_upInitial%BL bl;


    public %tableName_upInitial%ListAction() {
        this.bl = new %tableName_upInitial%BL();
        super.bl = this.bl;
        this.condition = new %tableName_upInitial%ListCondition();
    }


    /**
     * 根据查询条件得到列表
     *
     * @throws Exception
     */
    public String myExecute() throws Exception {
        page.setWhereCondition(getConditionString());
//        page.setOrderBy(" nrOccur DESC");
       
        bl.getList(page);

        return SUCCESS;
    }


    public void checkCondition() {
        %tableName_upInitial%ListCondition condition = (%tableName_upInitial%ListCondition) this.condition;

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

    public String getConditionString() {
        %tableName_upInitial%ListCondition condition = (%tableName_upInitial%ListCondition) this.condition;

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
            sb.append(" AND id IN (SELECT %tableName%Id from %tableName%Name where name ");
            if("true".equals(condition.fuzzyMatch)) {
                sb.append(" LIKE '%").append(condition.name).append("%'");
            } else {
                sb.append(" = '").append(condition.name).append("'");
            }
            sb.append(")");
        }
        return sb.toString();
    }

}