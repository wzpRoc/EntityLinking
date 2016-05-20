package org.ailab.tem.wim.frequentlyQuestion;

import org.ailab.tem.wim.frequentlyQuestion.FrequentlyQuestion;
import org.ailab.tem.wim.frequentlyQuestion.FrequentlyQuestionBL;
import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-22
 * 功能描述：常见问题业务逻辑
 */


public class FrequentlyQuestionDetailAction extends BaseDetailAction {
    public FrequentlyQuestionDetailAction() {
        dto = new FrequentlyQuestion();
        bl = new FrequentlyQuestionBL();
    }
}
