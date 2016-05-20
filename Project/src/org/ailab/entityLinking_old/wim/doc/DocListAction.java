package org.ailab.entityLinking_old.wim.doc;

import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;



/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文章业务逻辑
 */
public class DocListAction extends BaseListAction {
    private DocBL bl;


    public DocListAction() {
        this.bl = new DocBL();
        super.bl = this.bl;
        this.condition = new DocListCondition();
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
        DocListCondition condition = (DocListCondition) this.condition;

        if (StringUtil.isEmpty(condition.updaterId)) {
            condition.updaterId = "";
        }

        if (StringUtil.isEmpty(condition.title)) {
            condition.title = "";
        }

        if (StringUtil.isEmpty(condition.fuzzyMatch )) {
            condition.fuzzyMatch = "";
        }

        if (StringUtil.isEmpty(condition.annoState)) {
            condition.annoState = "";
        }
    }

    public String getConditionString() {
        DocListCondition condition = (DocListCondition) this.condition;

        // 处理查询条件
        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");

        if (StringUtil.notEmpty(condition.annoState)) {
            sb.append(" AND annoState=").append(condition.annoState).append(" ");
        }
        if (StringUtil.notEmpty(condition.updaterId)) {
            sb.append(" AND updaterId=").append(condition.updaterId).append(" ");
        }
        if (StringUtil.notEmpty(condition.title)) {
            sb.append(" AND title like '%").append(condition.title).append("%' ");
        }
        return sb.toString();
    }

}
