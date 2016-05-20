package org.ailab.entityLinking_old.wim.linkResult;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-18
 * 功能描述：连接结果业务逻辑
 */
public class LinkResultBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(LinkResultBL.class);
    protected LinkResultDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<LinkResult> dtoList;
    public static Map<Integer, LinkResult> idToDtoMap;

    static {
        try {
            LinkResultBL projectBL = new LinkResultBL();
            linkResultBL.reloadCache();
            ValueAndLabelListCache.register("linkResult", linkResultBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static LinkResult getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = LinkResultBL.getByIdFromCache(LinkResultBL.class, id);
        if (dto != null) {
            return ((LinkResult) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public LinkResultBL(){
        this.dao = new LinkResultDAO();
        super.dao = this.dao;
    }


}
