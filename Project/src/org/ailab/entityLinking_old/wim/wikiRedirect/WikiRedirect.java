package org.ailab.entityLinking_old.wim.wikiRedirect;

import org.ailab.wimfra.core.BaseDTO;

/**
 * User: Lu Tingming
 * Date: 15-5-15
 * Time: 下午3:01
 * Desc:
 */
public class WikiRedirect extends BaseDTO {
    protected String fromTitle;
    protected int toId;

    public String getFromTitle() {
        return fromTitle;
    }

    public void setFromTitle(String fromTitle) {
        this.fromTitle = fromTitle;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }
}
