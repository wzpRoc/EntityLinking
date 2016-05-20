package org.ailab.entityLinking_old.wim.doc;
import org.ailab.wimfra.core.ListCondition;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文章业务逻辑
 */
public class DocListCondition extends ListCondition {
    protected String title;
    protected String updaterId;
    protected String annoState;
    protected String fuzzyMatch;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getAnnoState() {
        return annoState;
    }

    public void setAnnoState(String annoState) {
        this.annoState = annoState;
    }

    public String getFuzzyMatch() {
        return fuzzyMatch;
    }

    public void setFuzzyMatch(String fuzzyMatch) {
        this.fuzzyMatch = fuzzyMatch;
    }
}
