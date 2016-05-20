package org.ailab.wikipedia.wim.category;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：类别业务逻辑
 */


public class CategoryDetailAction extends BaseDetailAction {
    public CategoryDetailAction() {
        dto = new Category();
        bl = new CategoryBL();
    }
}
