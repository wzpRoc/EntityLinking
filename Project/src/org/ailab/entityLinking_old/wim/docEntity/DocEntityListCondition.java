package org.ailab.entityLinking_old.wim.docEntity;
import org.ailab.wimfra.core.ListCondition;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档实体业务逻辑
 */
public class DocEntityListCondition extends ListCondition {
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
