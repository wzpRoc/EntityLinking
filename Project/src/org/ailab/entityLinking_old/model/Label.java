package org.ailab.entityLinking_old.model;

/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午11:18
 * Desc:
 */
public enum Label {
    yes(1), no(0), unknown(-1);
    private int value;

    private Label(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
