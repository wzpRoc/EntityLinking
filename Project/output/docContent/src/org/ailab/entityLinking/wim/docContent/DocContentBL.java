package org.ailab.entityLinking.wim.docContent;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体名称业务逻辑
 */
public class DocContentBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocContentBL.class);
    protected DocContentDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<DocContent> dtoList;
    public static Map<Integer, DocContent> idToDtoMap;

    static {
        try {
            DocContentBL projectBL = new DocContentBL();
            docContentBL.reloadCache();
            ValueAndLabelListCache.register("docContent", docContentBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static DocContent getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = DocContentBL.getByIdFromCache(DocContentBL.class, id);
        if (dto != null) {
            return ((DocContent) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public DocContentBL(){
        this.dao = new DocContentDAO();
        super.dao = this.dao;
    }


}
