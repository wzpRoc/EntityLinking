package org.ailab.entityLinking_old.wim.entityName;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-12
 * 功能描述：实体名称业务逻辑
 */


public class EntityNameDetailAction extends BaseDetailAction {
    public EntityNameDetailAction() {
        dto = new EntityName();
        bl = new EntityNameBL();
    }
}
