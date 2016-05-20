package org.ailab.tem.wim.userGroupPrivilege;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-06-21
 * 功能描述：用户组权限表业务逻辑
 */
public class UserGroupPrivilegeBL extends BaseBL {
	/** 日志工具 */
    private Logger logger = Logger.getLogger(UserGroupPrivilegeBL.class);
    protected UserGroupPrivilegeDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<UserGroupPrivilege> dtoList;
    public static Map<Integer, UserGroupPrivilege> idToDtoMap;

    static {
        try {
            UserGroupPrivilegeBL projectBL = new UserGroupPrivilegeBL();
            userGroupPrivilegeBL.reloadCache();
            ValueAndLabelListCache.register("userGroupPrivilege", userGroupPrivilegeBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static UserGroupPrivilege getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserGroupPrivilegeBL.getByIdFromCache(UserGroupPrivilegeBL.class, id);
        if (dto != null) {
            return ((UserGroupPrivilege) dto);
        } else {
            return null;
        }
    }
    */
   ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public UserGroupPrivilegeBL(){
        this.dao = new UserGroupPrivilegeDAO();
        super.dao = this.dao;
    }


    public void deleteByGroupId(int groupId) throws SQLException {
        dao.deleteByGroupId(groupId);
    }

}
