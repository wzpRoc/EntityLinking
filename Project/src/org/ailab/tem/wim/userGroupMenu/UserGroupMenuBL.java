package org.ailab.tem.wim.userGroupMenu;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.Vector;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组菜单业务逻辑
 */
public class UserGroupMenuBL extends BaseBL {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(UserGroupMenuBL.class);
    protected UserGroupMenuDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<UserGroupMenu> dtoList;
    public static Map<Integer, UserGroupMenu> idToDtoMap;

    static {
        try {
            UserGroupMenuBL projectBL = new UserGroupMenuBL();
            userGroupMenuBL.reloadCache();
            ValueAndLabelListCache.register("userGroupMenu", userGroupMenuBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static UserGroupMenu getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserGroupMenuBL.getByIdFromCache(UserGroupMenuBL.class, id);
        if (dto != null) {
            return ((UserGroupMenu) dto);
        } else {
            return null;
        }
    }
    */
    ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public UserGroupMenuBL() {
        this.dao = new UserGroupMenuDAO();
        super.dao = this.dao;
    }

    public Set<Integer> getMenuIdSetByGroupId(int groupId) throws SQLException {
        return dao.getMenuIdSetByGroupId(groupId);
    }


    public int deleteByGroupId(int groupId) throws SQLException {
        return dao.deleteByGroupId(groupId);
    }


}
