package org.ailab.entityLinking_old.wim.entity;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：实体业务逻辑
 */


public class EntityDetailAction extends BaseDetailAction {
    public EntityDetailAction() {
        dto = new Entity();
        bl = new EntityBL();
    }
}
