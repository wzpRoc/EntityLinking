package org.ailab.tem.wim.agentInfo;
import org.ailab.wimfra.core.ListCondition;

/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：代理商业务逻辑 ListCondition
 * 目前状态：暂未使用
 */
public class AgentInfoListCondition extends ListCondition {
    protected String category;
    protected String countryId;
    protected String name;
    protected String fuzzyMatch;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFuzzyMatch() {
        return fuzzyMatch;
    }

    public void setFuzzyMatch(String fuzzyMatch) {
        this.fuzzyMatch = fuzzyMatch;
    }
}
