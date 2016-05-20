package org.ailab.entityLinking.wim.wikiFact;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;



/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiFactListAction extends BaseListAction {
    private WikiFactBL bl;


    public WikiFactListAction() {
        this.bl = new WikiFactBL();
        super.bl = this.bl;
        this.condition = new WikiFactListCondition();
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
        WikiFactListCondition condition = (WikiFactListCondition) this.condition;

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
        WikiFactListCondition condition = (WikiFactListCondition) this.condition;

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
            sb.append(" AND id IN (SELECT wikiFactId from wikiFactName where name ");
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
