package org.ailab.entityLinking.wim.entityName;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-12
 * 功能描述：实体名称业务逻辑
 */
public class EntityNameBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(EntityNameBL.class);
    protected EntityNameDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<EntityName> dtoList;
    public static Map<Integer, EntityName> idToDtoMap;

    static {
        try {
            EntityNameBL projectBL = new EntityNameBL();
            entityNameBL.reloadCache();
            ValueAndLabelListCache.register("entityName", entityNameBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static EntityName getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = EntityNameBL.getByIdFromCache(EntityNameBL.class, id);
        if (dto != null) {
            return ((EntityName) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public EntityNameBL(){
        this.dao = new EntityNameDAO();
        super.dao = this.dao;
    }


}
