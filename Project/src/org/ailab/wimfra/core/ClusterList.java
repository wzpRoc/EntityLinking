package org.ailab.wimfra.core;

import java.util.ArrayList;

/**
 * User: Lu Tingming
 * Date: 2012-6-26
 * Time: 3:09:01
 * Desc:
 */
public class ClusterList extends ArrayList<LabelAndLink> {
    protected String title;

    public ClusterList(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
