package org.ailab.tem.wim.menu;

import org.ailab.tem.wim.user.User;
import org.ailab.tem.wim.user.UserBL;
import org.ailab.tem.wim.user.UserRole;
import org.ailab.tem.wim.userGroupUser.UserGroupUserBL;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：菜单业务逻辑
 */
public class MenuBL extends BaseBL {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(MenuBL.class);
    protected MenuDAO dao;


    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    /*
    public static boolean isCacheEnabled = true;
    public static List<Menu> dtoList;
    public static Map<Integer, Menu> idToDtoMap;

    static {
        try {
            MenuBL projectBL = new MenuBL();
            menuBL.reloadCache();
            ValueAndLabelListCache.register("menu", menuBL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 从缓存中获得对象
     */
    /*
    public static Menu getFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        BaseDTO dto = MenuBL.getByIdFromCache(MenuBL.class, id);
        if (dto != null) {
            return ((Menu) dto);
        } else {
            return null;
        }
    }
    */
    ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 构造函数
     */
    public MenuBL() {
        this.dao = new MenuDAO();
        super.dao = this.dao;
    }


    /**
     * 将menuList转化为树形目录结构
     *
     * @param allMenuList
     * @return
     */
    public Menu getRootMenuWithOffspringWithMenuList(List<Menu> allMenuList) {
        Menu rootMenu = new Menu();
        if (allMenuList == null) {
            return rootMenu;
        }

        // 创建映射表
        Map<Integer, Menu> idToMenuMap = new HashMap<Integer, Menu>(allMenuList.size());
        for (Menu menu : allMenuList) {
            idToMenuMap.put(menu.getId(), menu);
        }

        // 创建树形
        for (Menu menu : allMenuList) {
            if (menu.getParentId() == 0) {
                rootMenu.addChild(menu);
            } else {
                Menu parentMenu = idToMenuMap.get(menu.getParentId());
                if (parentMenu == null) {
                    logger.warn("menu is null where id = " + menu.getParentId());
                } else {
                    parentMenu.addChild(menu);
                }
            }
        }
        return rootMenu;
    }

    /**
     * 根据用户组id集合获得所拥有的目录结构
     *
     * @param groupIdSet
     * @return
     * @throws java.sql.SQLException
     */
    public Menu getRootMenuWithOffspringByGroupIds(Set<Integer> groupIdSet) throws SQLException {
        List<Menu> allMenuList = getMenuListByGroupIds(groupIdSet);
        return getRootMenuWithOffspringWithMenuList(allMenuList);
    }


    public List<Menu> getMenuListByGroupIds(Set<Integer> groupIdSet) throws SQLException {
        return getMenuListByGroupIds(StringUtil.collectionToString(groupIdSet, ","));
    }

    public List<Menu> getMenuListByGroupIds(String groupIds) throws SQLException {
        if (StringUtil.isEmpty(groupIds)) {
            return null;
        }
        return dao.getMenuListByGroupIds(groupIds);
    }

    public List<Menu> getMenuListByMenuIdSet(Set<Integer> menuIdSet) throws SQLException {
        return getMenuListByGroupIds(StringUtil.collectionToString(menuIdSet, ","));
    }

    public List<Menu> getMenuListByMenuIds(String menuIds) throws SQLException {
        return dao.getMenuListByMenuIds(menuIds);
    }

}
