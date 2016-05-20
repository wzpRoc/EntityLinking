package org.ailab.entityLinking.wim.wikiFact;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiFactBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(WikiFactBL.class);
    protected WikiFactDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<WikiFact> dtoList;
    public static Map<Integer, WikiFact> idToDtoMap;

    static {
        try {
            WikiFactBL projectBL = new WikiFactBL();
            wikiFactBL.reloadCache();
            ValueAndLabelListCache.register("wikiFact", wikiFactBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static WikiFact getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = WikiFactBL.getByIdFromCache(WikiFactBL.class, id);
        if (dto != null) {
            return ((WikiFact) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public WikiFactBL(){
        this.dao = new WikiFactDAO();
        super.dao = this.dao;
    }


}
