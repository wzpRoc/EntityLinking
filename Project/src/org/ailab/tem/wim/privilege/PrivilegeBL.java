package org.ailab.tem.wim.privilege;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-06-21
 * 功能描述：权限表业务逻辑
 */
public class PrivilegeBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(PrivilegeBL.class);
    protected PrivilegeDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Privilege> dtoList;
    public static Map<Integer, Privilege> idToDtoMap;

    static {
        try {
            PrivilegeBL projectBL = new PrivilegeBL();
            privilegeBL.reloadCache();
            ValueAndLabelListCache.register("privilege", privilegeBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Privilege getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = PrivilegeBL.getByIdFromCache(PrivilegeBL.class, id);
        if (dto != null) {
            return ((Privilege) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public PrivilegeBL(){
        this.dao = new PrivilegeDAO();
        super.dao = this.dao;
    }


}
