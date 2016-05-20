package org.ailab.tem.wim.aboutUs;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;


/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：关于我们 DetailAction
 */
public class AboutUsListAction extends BaseListAction {

    // 关于我们业务逻辑
    private AboutUsBL bl;

    /**
     * 构造函数
     */
    public AboutUsListAction() {
        this.bl = new AboutUsBL();
        super.bl = this.bl;
        this.condition = new AboutUsListCondition();
    }


    /**
     * 根据查询条件得到列表
     * @throws Exception
     */
    public String myExecute() throws Exception {
        // 完成查询条件的载入
        page.setWhereCondition(getConditionString());
//        page.setOrderBy(" nrOccur DESC");
        // 完成查询
        bl.getList(page);

        return SUCCESS;
    }

    /**
     * 初始查询条件检查
     * 目前状态：暂未使用
     */
    public void checkCondition() {
        AboutUsListCondition condition = (AboutUsListCondition) this.condition;

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
        AboutUsListCondition condition = (AboutUsListCondition) this.condition;

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
            sb.append(" AND id IN (SELECT aboutUsId from aboutUsName where name ");
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
