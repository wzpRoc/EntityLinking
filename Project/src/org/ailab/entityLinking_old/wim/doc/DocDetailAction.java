package org.ailab.entityLinking_old.wim.doc;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文章业务逻辑
 */


public class DocDetailAction extends BaseDetailAction {
    public DocDetailAction() {
        dto = new Doc();
        bl = new DocBL();
    }
}
