package org.ailab.entityLinking.wim.docContent;

import org.ailab.entityLinking.wim.docContent.DocContent;
import org.ailab.entityLinking.wim.docContent.DocContentBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体名称业务逻辑
 */


public class DocContentDetailAction extends BaseDetailAction {
    public DocContentDetailAction() {
        dto = new DocContent();
        bl = new DocContentBL();
    }
}
