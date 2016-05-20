package org.ailab.entityLinking.wim.entityToEntityLink;

import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLink;
import org.ailab.entityLinking.wim.entityToEntityLink.EntityToEntityLinkBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体到实体的链接业务逻辑
 */


public class EntityToEntityLinkDetailAction extends BaseDetailAction {
    public EntityToEntityLinkDetailAction() {
        dto = new EntityToEntityLink();
        bl = new EntityToEntityLinkBL();
    }
}
