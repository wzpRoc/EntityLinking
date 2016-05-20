package org.ailab.wimfra.core;

/**
 * User: Lu Tingming
 * Date: 2012-4-18
 * Time: 15:29:22
 * Desc:
 */
public class ValueAndLabel implements IValueAndLabel {
    public String value;
    public String text;

    public ValueAndLabel(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public ValueAndLabel(int value, String text) {
        this.value = String.valueOf(value);
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
