package org.ailab.entityLinking.wim.doc_lob;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档大对象业务逻辑
 */
public class Doc_lobBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(Doc_lobBL.class);
    protected Doc_lobDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Doc_lob> dtoList;
    public static Map<Integer, Doc_lob> idToDtoMap;

    static {
        try {
            Doc_lobBL projectBL = new Doc_lobBL();
            doc_lobBL.reloadCache();
            ValueAndLabelListCache.register("doc_lob", doc_lobBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Doc_lob getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = Doc_lobBL.getByIdFromCache(Doc_lobBL.class, id);
        if (dto != null) {
            return ((Doc_lob) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public Doc_lobBL(){
        this.dao = new Doc_lobDAO();
        super.dao = this.dao;
    }


}
