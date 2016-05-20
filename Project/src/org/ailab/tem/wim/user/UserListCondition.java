package org.ailab.tem.wim.user;
import org.ailab.wimfra.core.ListCondition;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-12
 * 功能描述：用户业务逻辑
 */
public class UserListCondition extends ListCondition {
    protected String category;
    protected String countryId;
    protected String name;
    protected String id;
    protected String fuzzyMatch;

    protected String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
