package org.ailab.tem.wim.frequentlyQuestion;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-22
 * 功能描述：常见问题业务逻辑
 */
public class FrequentlyQuestionBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(FrequentlyQuestionBL.class);
    protected FrequentlyQuestionDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<FrequentlyQuestion> dtoList;
    public static Map<Integer, FrequentlyQuestion> idToDtoMap;

    static {
        try {
            FrequentlyQuestionBL projectBL = new FrequentlyQuestionBL();
            frequentlyQuestionBL.reloadCache();
            ValueAndLabelListCache.register("frequentlyQuestion", frequentlyQuestionBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static FrequentlyQuestion getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = FrequentlyQuestionBL.getByIdFromCache(FrequentlyQuestionBL.class, id);
        if (dto != null) {
            return ((FrequentlyQuestion) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public FrequentlyQuestionBL(){
        this.dao = new FrequentlyQuestionDAO();
        super.dao = this.dao;
    }


    public int deleteByCategoryId(int categoryId) throws SQLException {
        return dao.deleteByCategoryId(categoryId);
    }

}
