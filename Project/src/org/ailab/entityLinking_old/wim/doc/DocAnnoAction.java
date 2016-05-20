package org.ailab.entityLinking_old.wim.doc;

import org.ailab.wimfra.core.BaseDetailAction;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文章业务逻辑
 */


public class DocAnnoAction extends BaseDetailAction {
    public DocAnnoAction() {
        dto = new Doc();
        bl = new DocBL();
    }

    /**
     * 获得一条记录
     */
    public void getDtoFromDB() throws Exception {
        this.dto = ((DocBL)bl).getWithAnno(Integer.parseInt(id));
    }



}
