package org.ailab.common.config;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-26
 * 功能描述：专辑业务逻辑
 */


public class ConfigDetailAction extends BaseDetailAction {
    public ConfigDetailAction() {
        dto = new Config();
        bl = new ConfigBL();
    }
}
