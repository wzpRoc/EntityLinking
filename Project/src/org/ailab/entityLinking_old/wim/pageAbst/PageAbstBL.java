package org.ailab.entityLinking_old.wim.pageAbst;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-15
 * 功能描述：维基页面摘要业务逻辑
 */
public class PageAbstBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(PageAbstBL.class);
    protected PageAbstDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<PageAbst> dtoList;
    public static Map<Integer, PageAbst> idToDtoMap;

    static {
        try {
            PageAbstBL projectBL = new PageAbstBL();
            pageAbstBL.reloadCache();
            ValueAndLabelListCache.register("pageAbst", pageAbstBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static PageAbst getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = PageAbstBL.getByIdFromCache(PageAbstBL.class, id);
        if (dto != null) {
            return ((PageAbst) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public PageAbstBL(){
        this.dao = new PageAbstDAO();
        super.dao = this.dao;
    }


}
