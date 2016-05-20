package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.wimfra.core.BaseDTO;

/**
 * User: WZP
 * Date: 16-1-3
 * Time: 下午11:10
 */
public class PageAbst extends BaseDTO {
    public int id;
    public String title;
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

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }
}
