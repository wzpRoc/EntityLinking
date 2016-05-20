package org.ailab.common.country;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-04
 * 功能描述：国家和地区业务逻辑
 */
public class CountryBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(CountryBL.class);
    protected CountryDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////

    public static boolean isCacheEnabled = true;
    public static List<Country> dtoList;
    public static Map<Integer, Country> idToDtoMap;

    static {
        try {
            new CountryBL().reloadCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从缓存中获得对象
     */

    public static Country getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = CountryBL.getByIdFromCache(CountryBL.class, id);
        if (dto != null) {
            return ((Country) dto);
        } else {
            return null;
        }
    }

   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public CountryBL(){
        this.dao = new CountryDAO();
        super.dao = this.dao;
    }


}
