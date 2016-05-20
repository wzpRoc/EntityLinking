package org.ailab.entityLinking_old.experiment;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 下午10:12
 * Desc:
 */
public enum SelectMethod {
    random("random"), activeLearning("activeLearning");

    private String name;

    private SelectMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
