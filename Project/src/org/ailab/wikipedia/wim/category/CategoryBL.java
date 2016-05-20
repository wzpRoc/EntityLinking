package org.ailab.wikipedia.wim.category;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：类别业务逻辑
 */
public class CategoryBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(CategoryBL.class);
    protected CategoryDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Category> dtoList;
    public static Map<Integer, Category> idToDtoMap;

    static {
        try {
            CategoryBL projectBL = new CategoryBL();
            categoryBL.reloadCache();
            ValueAndLabelListCache.register("category", categoryBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Category getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = CategoryBL.getByIdFromCache(CategoryBL.class, id);
        if (dto != null) {
            return ((Category) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public CategoryBL(){
        this.dao = new CategoryDAO();
        super.dao = this.dao;
    }


}
