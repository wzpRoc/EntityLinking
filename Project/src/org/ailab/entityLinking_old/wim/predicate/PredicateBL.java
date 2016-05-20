package org.ailab.entityLinking_old.wim.predicate;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：谓词业务逻辑
 */
public class PredicateBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(PredicateBL.class);
    protected PredicateDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Predicate> dtoList;
    public static Map<Integer, Predicate> idToDtoMap;

    static {
        try {
            PredicateBL projectBL = new PredicateBL();
            predicateBL.reloadCache();
            ValueAndLabelListCache.register("predicate", predicateBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Predicate getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = PredicateBL.getByIdFromCache(PredicateBL.class, id);
        if (dto != null) {
            return ((Predicate) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public PredicateBL(){
        this.dao = new PredicateDAO();
        super.dao = this.dao;
    }


}
