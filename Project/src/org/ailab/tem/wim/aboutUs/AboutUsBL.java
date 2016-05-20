package org.ailab.tem.wim.aboutUs;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;



/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：关于我们业务逻辑
 */
public class AboutUsBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(AboutUsBL.class);
    protected AboutUsDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<AboutUs> dtoList;
    public static Map<Integer, AboutUs> idToDtoMap;

    static {
        try {
            AboutUsBL projectBL = new AboutUsBL();
            aboutUsBL.reloadCache();
            ValueAndLabelListCache.register("aboutUs", aboutUsBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static AboutUs getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = AboutUsBL.getByIdFromCache(AboutUsBL.class, id);
        if (dto != null) {
            return ((AboutUs) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public AboutUsBL(){
        this.dao = new AboutUsDAO();
        super.dao = this.dao;
    }


}
