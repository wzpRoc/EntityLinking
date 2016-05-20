package org.ailab.tool.wikiProcess.p1_wikiText;

import org.ailab.wimfra.core.BaseDTO;

/**
 * User: WZP
 * Date: 16-1-3
 * Time: 下午11:10
 */
public class PageWikiText extends BaseDTO {
    public int id;
    public int entityId;
    public String title;
    public String wikiText;
    public String plainText;
    public String abst;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWikiText() {
        return wikiText;
    }

    public void setWikiText(String wikiText) {
        this.wikiText = wikiText;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
