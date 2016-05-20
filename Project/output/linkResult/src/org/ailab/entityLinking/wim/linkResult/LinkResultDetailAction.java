package org.ailab.entityLinking.wim.linkResult;

import org.ailab.entityLinking.wim.linkResult.LinkResult;
import org.ailab.entityLinking.wim.linkResult.LinkResultBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-18
 * 功能描述：连接结果业务逻辑
 */


public class LinkResultDetailAction extends BaseDetailAction {
    public LinkResultDetailAction() {
        dto = new LinkResult();
        bl = new LinkResultBL();
    }
}
