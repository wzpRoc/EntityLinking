package org.ailab.tem.wim.language;

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
 * 功能描述：语种业务逻辑
 */
public class LanguageBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(LanguageBL.class);
    protected LanguageDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////

    public static boolean isCacheEnabled = true;
    public static List<Language> dtoList;
    public static Map<Integer, Language> idToDtoMap;

    static {
        try {
            new LanguageBL().reloadCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从缓存中获得对象
     */

    public static Language getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = LanguageBL.getByIdFromCache(LanguageBL.class, id);
        if (dto != null) {
            return ((Language) dto);
        } else {
            return null;
        }
    }

   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public LanguageBL(){
        this.dao = new LanguageDAO();
        super.dao = this.dao;
    }


}
