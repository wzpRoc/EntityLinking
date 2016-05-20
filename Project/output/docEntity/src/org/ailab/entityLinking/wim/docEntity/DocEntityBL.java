package org.ailab.entityLinking.wim.docEntity;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档实体业务逻辑
 */
public class DocEntityBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(DocEntityBL.class);
    protected DocEntityDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<DocEntity> dtoList;
    public static Map<Integer, DocEntity> idToDtoMap;

    static {
        try {
            DocEntityBL projectBL = new DocEntityBL();
            docEntityBL.reloadCache();
            ValueAndLabelListCache.register("docEntity", docEntityBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static DocEntity getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = DocEntityBL.getByIdFromCache(DocEntityBL.class, id);
        if (dto != null) {
            return ((DocEntity) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public DocEntityBL(){
        this.dao = new DocEntityDAO();
        super.dao = this.dao;
    }


}
