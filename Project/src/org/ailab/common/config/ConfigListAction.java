package org.ailab.common.config;


import org.ailab.wimfra.core.BaseListAction;
import org.ailab.wimfra.util.StringUtil;

/**
 * User: Lu Tingming
 * Date: 2012-06-17
 */
public class ConfigListAction extends BaseListAction {
    public String reloadFromCache;


    public ConfigListAction() {
        bl = new ConfigBL();
        condition = new ConfigListCondition();
    }


    /**
     * 根据查询条件得到列表
     *
     * @throws Exception
     */
    public String execute() throws Exception {
        super.execute();

        if("1".equals(reloadFromCache) || "true".equalsIgnoreCase(reloadFromCache)) {
            ((ConfigBL) bl).reloadCache();
        }
        reloadFromCache = "0";

        page.setRecordNumPerPage(10000000);
        page.setWhereCondition(getConditionString());
        bl.getList(page);

        return SUCCESS;
    }


    /**
     * 获得查询条件
     */
    public String getConditionString() {
        ConfigListCondition condition = (ConfigListCondition) this.condition;
        StringBuilder sb = new StringBuilder();
        if(StringUtil.notEmpty(condition.getModule())) {
            sb.append("module='"+condition.getModule()+"'");
        }

        return sb.toString();
    }


    public String getReloadFromCache() {
        return reloadFromCache;
    }

    public void setReloadFromCache(String reloadFromCache) {
        this.reloadFromCache = reloadFromCache;
    }
}