package org.ailab.wimfra.core;

/**
 * User: Lu Tingming
 * Date: 2012-6-26
 * Time: 3:13:39
 * Desc:
 */
public class LabelAndLink {
    protected String label;
    protected String link;

    public LabelAndLink() {
    }

    public LabelAndLink(String label, String link) {
        this.label = label;
        this.link = link;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
