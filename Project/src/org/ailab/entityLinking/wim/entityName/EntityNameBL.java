package org.ailab.entityLinking.wim.entityName;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.util.List;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-20
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


    public List<EntityName> getList(double minProbOfNameToEntity) {
        return getListByCondition("entityId>0 and probOfNameToEntity>="+minProbOfNameToEntity);
    }


    /**
     * 根据名称查找
     * @param name
     * @return
     */
    public List<EntityName> getListByName(String name) {
        return dao.getListByName(name);
    }


    /**
     * 根据小写名称查找
     * @param lcName
     * @return
     */
    public List<EntityName> getListByLcName(String lcName) {
        return dao.getListByLcName(lcName);
    }

    /**
     * 根据名称查找，选择最大的概率
     * @param name
     * @return
     */
    public List<EntityName> getMaxListByName(String name) {
        return dao.getMaxListByName(name);
    }

    /**
     * 根据小写名称查找，并汇总
     * @param name
     * @return
     */
    public List<EntityName> getAverageListByLcName(String name) {
        return dao.getAverageListByLcName(name);
    }
}

