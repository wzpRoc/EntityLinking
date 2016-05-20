package org.ailab.entityLinking.wim.wikiFact;

import org.ailab.entityLinking.wim.wikiFact.WikiFact;
import org.ailab.entityLinking.wim.wikiFact.WikiFactBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */


public class WikiFactDetailAction extends BaseDetailAction {
    public WikiFactDetailAction() {
        dto = new WikiFact();
        bl = new WikiFactBL();
    }
}
