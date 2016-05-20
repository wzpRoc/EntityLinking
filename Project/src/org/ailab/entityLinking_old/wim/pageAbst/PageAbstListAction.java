package org.ailab.entityLinking_old.wim.pageAbst;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;



/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-15
 * 功能描述：维基页面摘要业务逻辑
 */
public class PageAbstListAction extends BaseListAction {
    private PageAbstBL bl;


    public PageAbstListAction() {
        this.bl = new PageAbstBL();
        super.bl = this.bl;
        this.condition = new PageAbstListCondition();
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
        PageAbstListCondition condition = (PageAbstListCondition) this.condition;

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
        PageAbstListCondition condition = (PageAbstListCondition) this.condition;

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
            sb.append(" AND id IN (SELECT pageAbstId from pageAbstName where name ");
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
