package org.ailab.common.language;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-04
 * 功能描述：语种业务逻辑
 */


public class LanguageDetailAction extends BaseDetailAction {
    public LanguageDetailAction() {
        dto = new Language();
        bl = new LanguageBL();
    }
}
