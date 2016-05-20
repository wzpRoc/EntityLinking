package org.ailab.entityLinking_old.wim.pageAbst;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-15
 * 功能描述：维基页面摘要业务逻辑
 */


public class PageAbstDetailAction extends BaseDetailAction {
    public PageAbstDetailAction() {
        dto = new PageAbst();
        bl = new PageAbstBL();
    }
}
