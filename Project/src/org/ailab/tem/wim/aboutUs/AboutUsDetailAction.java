package org.ailab.tem.wim.aboutUs;


import org.ailab.wimfra.core.BaseDetailAction;

/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：关于我们 ListAction
 */

public class AboutUsDetailAction extends BaseDetailAction {

    /**
     * 构造函数
     */
    public AboutUsDetailAction() {
        dto = new AboutUs();
        bl = new AboutUsBL();
    }
}
