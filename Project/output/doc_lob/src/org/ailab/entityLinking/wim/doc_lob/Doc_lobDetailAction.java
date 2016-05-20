package org.ailab.entityLinking.wim.doc_lob;

import org.ailab.entityLinking.wim.doc_lob.Doc_lob;
import org.ailab.entityLinking.wim.doc_lob.Doc_lobBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档大对象业务逻辑
 */


public class Doc_lobDetailAction extends BaseDetailAction {
    public Doc_lobDetailAction() {
        dto = new Doc_lob();
        bl = new Doc_lobBL();
    }
}
