package org.ailab.tem.wim.userGroupMenu;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组菜单
 */
public class UserGroupMenu extends BaseDTO {
    // ID
    protected int id;

    // 用户组ID
    protected int userGroupId;

    // 菜单ID
    protected int menuId;

    public UserGroupMenu() {
    }

    public UserGroupMenu(int userGroupId, int menuId) {
        this.userGroupId = userGroupId;
        this.menuId = menuId;
    }

    /**
     * 得到ID
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 得到用户组ID
     *
     * @return 用户组ID
     */
    public int getUserGroupId() {
        return userGroupId;
    }

    /**
     * 设置用户组ID
     *
     * @param userGroupId 用户组ID
     */
    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    /**
     * 得到菜单ID
     *
     * @return 菜单ID
     */
    public int getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

}
