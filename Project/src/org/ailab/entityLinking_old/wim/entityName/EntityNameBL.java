package org.ailab.entityLinking_old.wim.entityName;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.util.List;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-12
 * 功能描述：实体名称业务逻辑
 */
public class EntityNameBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(EntityNameBL.class);
    protected EntityNameDAO dao;


    /**
     * 构造函数
     */
    public EntityNameBL(){
        this.dao = new EntityNameDAO();
        super.dao = this.dao;
    }

    public List<Integer> getEntityIdListByEntityName(String entityName) {
        return dao.getEntityIdListByEntityName(entityName);
    }

    public List<EntityName> getEntityNameListByPredicateName(String predicateName) {
        return dao.getEntityNameListByPredicateName(predicateName);
    }
}
