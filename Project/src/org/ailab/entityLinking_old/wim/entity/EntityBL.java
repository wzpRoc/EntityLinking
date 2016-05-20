package org.ailab.entityLinking_old.wim.entity;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.util.List;


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


    public List<Entity> queryEntity(String name, boolean fuzzy) throws SQLException {
        List<Entity> entityList = dao.queryEntity(name, fuzzy);
        // 精确匹配的排在第一个
        if(entityList!=null && entityList.size()>1) {
            int idxOfMatch = -1;
            for(int i=0; i<entityList.size(); i++) {
                Entity entity = entityList.get(i);
                // 如果title不匹配，应该按照名字来
                if(name.equalsIgnoreCase(entity.getTitle())) {
                    idxOfMatch = i;
                    break;
                }
            }
            if(idxOfMatch>=0) {
                Entity entityMatched = entityList.remove(idxOfMatch);
                entityList.add(0, entityMatched);
            }
        }
        return entityList;
    }

    public List<Entity> getPLOList() {
        return dao.getPLOList();
    }

}
