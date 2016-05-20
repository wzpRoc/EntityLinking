package org.ailab.wimfra.core;

/**
 * User: Lu Tingming
 * Date: 2012-4-25
 * Time: 20:52:57
 * Desc:
 */
public abstract class ListConditionWithDateRange extends ListCondition {
    protected String startDate;
    protected String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}