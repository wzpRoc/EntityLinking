package org.ailab.entityLinking.wim.docWordFreq;

import org.ailab.entityLinking.wim.docWordFreq.DocWordFreq;
import org.ailab.entityLinking.wim.docWordFreq.DocWordFreqBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：新闻数据中词出现的频次业务逻辑
 */


public class DocWordFreqDetailAction extends BaseDetailAction {
    public DocWordFreqDetailAction() {
        dto = new DocWordFreq();
        bl = new DocWordFreqBL();
    }
}
