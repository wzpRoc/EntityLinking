package org.ailab.wimfra.userLogger;


import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;

/**
 * User: ZhangQiang
 * Date: 13-11-24
 * Time: 下午9:46
 * Desc:
 */
public class UserLoggerListAction extends BaseListAction {
    private UserLoggerListCondition condition;


    public UserLoggerListAction() {
        this.condition = new UserLoggerListCondition();
        super.condition = this.condition;
        bl = new UserLoggerBL();
    }


    public String execute() throws Exception {
        super.execute();

        // 处理标题等字符串
        if(StringUtil.isEmpty(condition.dateBegin)){
            condition.dateBegin= TimeUtil.getYyyy_mm_dd();
        }
        if(StringUtil.isEmpty(condition.dateEnd)){
            condition.dateEnd=TimeUtil.getYyyy_mm_dd();
        }
        final String conditionString = getConditionString();

        page.setWhereCondition(conditionString);

        bl.getList(page);

        return SUCCESS;
    }


    public void checkCondition() {
        // 排序
        if (StringUtil.isEmpty(page.getOrderBy())) {
              page.setOrderBy("startTime Desc");
        }
    }

    public String getConditionString() throws Exception {
        // 处理查询条件
        StringBuffer sb = new StringBuffer();
        sb.append(" 1=1 ");

        if (StringUtil.notEmpty(condition.dateBegin)) {
            sb.append(" AND opDate >= '").append(condition.dateBegin).append("'");
        }
        if (StringUtil.notEmpty(condition.dateEnd)) {
            sb.append(" AND opDate <= '").append(condition.dateEnd).append("'");
        }
        if (StringUtil.notEmpty(condition.userId)) {
            sb.append(" AND userId = ").append(condition.userId);
        }
        if (StringUtil.notEmpty(condition.className)) {
            sb.append(" AND className= '").append(condition.className).append("'");
        }
        if (StringUtil.notEmpty(condition.method)) {
            sb.append("AND method= '").append(condition.method).append("'");
        }
        return sb.toString();
    }

    public String getPageTitle() {
        return "用户日志";
    }


}
