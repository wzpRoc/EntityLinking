package org.ailab.tem.wim.userGroupUser;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组成员
 */
public class UserGroupUser extends BaseDTO {
    // ID
    protected int id;

    // 用户组ID
    protected int userGroupId;

    // 用户ID
    protected int userId;

    // 用户角色
    protected int userState;

    public UserGroupUser() {
    }

    public UserGroupUser(int userId, int userGroupId) {
        this.userId = userId;
        this.userGroupId = userGroupId;
        this.userState = 1;
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
     * 得到用户ID
     *
     * @return 用户ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 得到用户角色
     *
     * @return 用户角色
     */
    public int getUserState() {
        return userState;
    }

    /**
     * 设置用户角色
     *
     * @param userState 用户角色
     */
    public void setUserState(int userState) {
        this.userState = userState;
    }

}
