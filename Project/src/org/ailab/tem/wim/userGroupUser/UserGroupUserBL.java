package org.ailab.tem.wim.userGroupUser;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组成员业务逻辑
 */
public class UserGroupUserBL extends BaseBL {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(UserGroupUserBL.class);
    protected UserGroupUserDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<UserGroupUser> dtoList;
    public static Map<Integer, UserGroupUser> idToDtoMap;

    static {
        try {
            UserGroupUserBL projectBL = new UserGroupUserBL();
            userGroupUserBL.reloadCache();
            ValueAndLabelListCache.register("userGroupUser", userGroupUserBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static UserGroupUser getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserGroupUserBL.getByIdFromCache(UserGroupUserBL.class, id);
        if (dto != null) {
            return ((UserGroupUser) dto);
        } else {
            return null;
        }
    }
    */
    ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public UserGroupUserBL() {
        this.dao = new UserGroupUserDAO();
        super.dao = this.dao;
    }

    /**
     * 根据用户ID获取用户所属组的ID集合
     *
     * @param userId
     * @return
     * @throws java.sql.SQLException
     */
    public Set<Integer> getGroupIdSetByUserId(int userId) throws SQLException {
        return dao.getGroupIdSetByUserId(userId);
    }

    public Set<Integer> getUserIdSetByGroupId(int groupId) throws SQLException {
        return dao.getUserIdSetByGroupId(groupId);
    }

    public int deleteByGroupId(int groupId) throws SQLException {
        return dao.deleteByGroupId(groupId);
    }


}
