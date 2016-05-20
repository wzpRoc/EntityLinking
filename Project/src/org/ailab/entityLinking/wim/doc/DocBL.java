package org.ailab.entityLinking.wim.doc;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-18
 * 功能描述：文档业务逻辑
 */
public class DocBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocBL.class);
    protected DocDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Doc> dtoList;
    public static Map<Integer, Doc> idToDtoMap;

    static {
        try {
            DocBL projectBL = new DocBL();
            docBL.reloadCache();
            ValueAndLabelListCache.register("doc", docBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Doc getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = DocBL.getByIdFromCache(DocBL.class, id);
        if (dto != null) {
            return ((Doc) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public DocBL(){
        this.dao = new DocDAO();
        super.dao = this.dao;
    }


}
