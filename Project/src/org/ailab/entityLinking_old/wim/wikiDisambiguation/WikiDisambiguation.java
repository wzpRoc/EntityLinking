package org.ailab.entityLinking_old.wim.wikiDisambiguation;

import org.ailab.wimfra.core.BaseDTO;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午3:01
 * Desc:
 */
public class WikiDisambiguation extends BaseDTO {
    protected String fromTitle;
    protected String toTitle;
    protected int toId;

    public String getFromTitle() {
        return fromTitle;
    }

    public void setFromTitle(String fromTitle) {
        this.fromTitle = fromTitle;
    }


    public String getToTitle() {
        return toTitle;
    }

    public void setToTitle(String toTitle) {
        this.toTitle = toTitle;
    }
}
