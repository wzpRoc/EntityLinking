package org.ailab.wikipedia.wim.infoboxItem;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：信息框条目业务逻辑
 */


public class InfoboxItemDetailAction extends BaseDetailAction {
    public InfoboxItemDetailAction() {
        dto = new InfoboxItem();
        bl = new InfoboxItemBL();
    }
}
