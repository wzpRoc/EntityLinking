package org.ailab.entityLinking.wim.mention;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：指称业务逻辑
 */
public class MentionBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(MentionBL.class);
    protected MentionDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Mention> dtoList;
    public static Map<Integer, Mention> idToDtoMap;

    static {
        try {
            MentionBL projectBL = new MentionBL();
            mentionBL.reloadCache();
            ValueAndLabelListCache.register("mention", mentionBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Mention getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = MentionBL.getByIdFromCache(MentionBL.class, id);
        if (dto != null) {
            return ((Mention) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public MentionBL(){
        this.dao = new MentionDAO();
        super.dao = this.dao;
    }


}
