package org.ailab.entityLinking.wim.candidateEntity;
import org.ailab.wimfra.core.ListCondition;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：指称的候选实体业务逻辑
 */
public class CandidateEntityListCondition extends ListCondition {
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