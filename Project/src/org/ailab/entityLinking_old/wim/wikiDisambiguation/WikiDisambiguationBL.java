package org.ailab.entityLinking_old.wim.wikiDisambiguation;

import org.ailab.wimfra.core.BaseBL;
import org.apache.log4j.Logger;



/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiDisambiguationBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(WikiDisambiguationBL.class);
    protected WikiDisambiguationDAO dao;


    /**
     * 构造函数
     */
    public WikiDisambiguationBL(){
        this.dao = new WikiDisambiguationDAO();
        super.dao = this.dao;
    }



}
