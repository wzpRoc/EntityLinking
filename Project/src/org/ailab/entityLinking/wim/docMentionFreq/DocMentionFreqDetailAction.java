package org.ailab.entityLinking.wim.docMentionFreq;

import org.ailab.entityLinking.wim.docMentionFreq.DocMentionFreq;
import org.ailab.entityLinking.wim.docMentionFreq.DocMentionFreqBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：实体名称业务逻辑
 */


public class DocMentionFreqDetailAction extends BaseDetailAction {
    public DocMentionFreqDetailAction() {
        dto = new DocMentionFreq();
        bl = new DocMentionFreqBL();
    }
}
