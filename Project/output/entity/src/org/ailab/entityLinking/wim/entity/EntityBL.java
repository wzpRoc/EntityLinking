package org.ailab.entityLinking.wim.entity;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：实体业务逻辑
 */
public class EntityBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(EntityBL.class);
    protected EntityDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Entity> dtoList;
    public static Map<Integer, Entity> idToDtoMap;

    static {
        try {
            EntityBL projectBL = new EntityBL();
            entityBL.reloadCache();
            ValueAndLabelListCache.register("entity", entityBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Entity getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = EntityBL.getByIdFromCache(EntityBL.class, id);
        if (dto != null) {
            return ((Entity) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public EntityBL(){
        this.dao = new EntityDAO();
        super.dao = this.dao;
    }


}
