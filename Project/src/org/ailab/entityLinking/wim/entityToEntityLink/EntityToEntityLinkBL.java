package org.ailab.entityLinking.wim.entityToEntityLink;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体到实体的链接业务逻辑
 */
public class EntityToEntityLinkBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(EntityToEntityLinkBL.class);
    protected EntityToEntityLinkDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<EntityToEntityLink> dtoList;
    public static Map<Integer, EntityToEntityLink> idToDtoMap;

    static {
        try {
            EntityToEntityLinkBL projectBL = new EntityToEntityLinkBL();
            entityToEntityLinkBL.reloadCache();
            ValueAndLabelListCache.register("entityToEntityLink", entityToEntityLinkBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static EntityToEntityLink getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = EntityToEntityLinkBL.getByIdFromCache(EntityToEntityLinkBL.class, id);
        if (dto != null) {
            return ((EntityToEntityLink) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public EntityToEntityLinkBL(){
        this.dao = new EntityToEntityLinkDAO();
        super.dao = this.dao;
    }


    /**
     * 获得一个实体的入链集合
     */
    public Set<Integer> getInlinkSet(int toEntityId) {
        return dao.getInlinkSet(toEntityId);
    }

    /**
     * 获得两个实体的入链交集大小
     */
    public int getIntersectionCount(int entityId0, int entityId1) {
        return dao.getIntersectionCount(entityId0, entityId1);
    }


}
