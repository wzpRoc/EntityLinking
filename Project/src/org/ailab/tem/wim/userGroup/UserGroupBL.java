package org.ailab.tem.wim.userGroup;

import org.ailab.tem.wim.menu.*;
import org.ailab.tem.wim.user.User;
import org.ailab.tem.wim.user.UserBL;
import org.ailab.tem.wim.userGroupMenu.UserGroupMenuBL;
import org.ailab.tem.wim.userGroupUser.UserGroupUserBL;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组业务逻辑
 */
public class UserGroupBL extends BaseBL {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(UserGroupBL.class);
    protected UserGroupDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<UserGroup> dtoList;
    public static Map<Integer, UserGroup> idToDtoMap;

    static {
        try {
            UserGroupBL projectBL = new UserGroupBL();
            userGroupBL.reloadCache();
            ValueAndLabelListCache.register("userGroup", userGroupBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static UserGroup getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = UserGroupBL.getByIdFromCache(UserGroupBL.class, id);
        if (dto != null) {
            return ((UserGroup) dto);
        } else {
            return null;
        }
    }
    */
    ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public UserGroupBL() {
        this.dao = new UserGroupDAO();
        super.dao = this.dao;
    }

    /**
     * 根据groupId查询出group中所有的user
     *
     * @param groupId
     * @return
     * @throws java.sql.SQLException
     */
    public List<User> getUserListByGroupId(int groupId) throws SQLException {
        UserGroupUserBL userGroupUserBL = new UserGroupUserBL();
        Set<Integer> userIdSet = userGroupUserBL.getUserIdSetByGroupId(groupId);

        UserBL userBL = new UserBL();
        List<User> userList = userBL.getUserListByUserIdSet(userIdSet);

        return userList;
    }

    /**
     * 获取所有的人员 并将是用户组的人标记出来
     *
     * @param groupId
     * @return
     * @throws java.sql.SQLException
     */
    public List<User> getAllUserListByGroupId(int groupId) throws SQLException {
        // 1 查询出group中人员的Id
        UserGroupUserBL userGroupUserBL = new UserGroupUserBL();
        Set<Integer> userIdSet = userGroupUserBL.getUserIdSetByGroupId(groupId);

        // 2 将是group中的人员标记出来
        UserBL userBL = new UserBL();
        List<User> allUserList = userBL.getListByCondition("role >= 11");
        for (User user : allUserList) {
            int userId = user.getId();
            if (userIdSet.contains(userId)) {
                user.setInGroup(true);
            }
        }

        return allUserList;
    }


    public List<Menu> getMenuListByGroupId(int groupId) throws SQLException {
        UserGroupMenuBL userGroupMenuBL = new UserGroupMenuBL();
        Set<Integer> menuIdSet = userGroupMenuBL.getMenuIdSetByGroupId(groupId);

        MenuBL menuBL = new MenuBL();
        List<Menu> menuList = menuBL.getMenuListByMenuIdSet(menuIdSet);

        return menuList;
    }

    public List<Menu> getAllMenuListByGroupId(int groupId) throws SQLException {
        UserGroupMenuBL userGroupMenuBL=new UserGroupMenuBL();
        Set<Integer> menuIdSet = userGroupMenuBL.getMenuIdSetByGroupId(groupId);

        MenuBL menuBL= new MenuBL();
        List<Menu> allMenuList=menuBL.getList();
        for(Menu menu: allMenuList){
            int menuId=menu.getId();
            if(menuIdSet.contains(menuId)){
                menu.setHavePermission(true);
            }
        }
        return allMenuList;
    }





}
