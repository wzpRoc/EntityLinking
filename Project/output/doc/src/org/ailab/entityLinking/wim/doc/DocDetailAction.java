package org.ailab.entityLinking.wim.doc;

import org.ailab.entityLinking.wim.doc.Doc;
import org.ailab.entityLinking.wim.doc.DocBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-18
 * 功能描述：文档业务逻辑
 */


public class DocDetailAction extends BaseDetailAction {
    public DocDetailAction() {
        dto = new Doc();
        bl = new DocBL();
    }
}
