package org.ailab.entityLinking.wim.mention;

import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.entityLinking.wim.mention.MentionBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：指称业务逻辑
 */


public class MentionDetailAction extends BaseDetailAction {
    public MentionDetailAction() {
        dto = new Mention();
        bl = new MentionBL();
    }
}
