package org.ailab.entityLinking_old.wim.wikiRedirect;

import org.ailab.wimfra.core.BaseBL;
import org.apache.log4j.Logger;


import java.util.HashMap;
import java.util.Map;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiRedirectBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(WikiRedirectBL.class);
    protected WikiRedirectDAO dao;


    /**
     * 构造函数
     */
    public WikiRedirectBL(){
        this.dao = new WikiRedirectDAO();
        super.dao = this.dao;
    }


    public Map<String, Integer> getNameToEntityIdMap() {
        return dao.getNameToEntityIdMap();
    }

    public HashMap<String, String> getNameToEntityTitleMap() {
        return dao.getNameToEntityTitleMap();
    }

}
