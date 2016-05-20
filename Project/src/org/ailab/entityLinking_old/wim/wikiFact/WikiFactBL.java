package org.ailab.entityLinking_old.wim.wikiFact;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiFactBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(WikiFactBL.class);
    protected WikiFactDAO dao;

    /**
     * 构造函数
     */
    public WikiFactBL(){
        this.dao = new WikiFactDAO();
        super.dao = this.dao;
    }


}
