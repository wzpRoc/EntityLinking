package org.ailab.wikipedia.wim.article;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：文章业务逻辑
 */


public class ArticleDetailAction extends BaseDetailAction {
    public ArticleDetailAction() {
        dto = new Article();
        bl = new ArticleBL();
    }
}
