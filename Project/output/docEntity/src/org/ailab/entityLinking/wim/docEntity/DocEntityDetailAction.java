package org.ailab.entityLinking.wim.docEntity;

import org.ailab.entityLinking.wim.docEntity.DocEntity;
import org.ailab.entityLinking.wim.docEntity.DocEntityBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档实体业务逻辑
 */


public class DocEntityDetailAction extends BaseDetailAction {
    public DocEntityDetailAction() {
        dto = new DocEntity();
        bl = new DocEntityBL();
    }
}
