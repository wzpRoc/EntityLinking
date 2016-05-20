package org.ailab.tem.wim.userGroupPrivilege;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-06-21
 * 功能描述：用户组权限表
 */
public class UserGroupPrivilege extends BaseDTO {
	// ID
	protected int id;

	// 用户组ID
	protected int userGroupId;

	// 权限ID
	protected int privilegeId;

    public UserGroupPrivilege() {}

    public UserGroupPrivilege(int userGroupId, int privilegeId) {
        this.userGroupId = userGroupId;
        this.privilegeId = privilegeId;
    }

	/**
	 * 得到ID
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置ID
	 * @param id ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 得到用户组ID
	 * @return 用户组ID
	 */
	public int getUserGroupId() {
		return userGroupId;
	}

	/**
	 * 设置用户组ID
	 * @param userGroupId 用户组ID
	 */
	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	/**
	 * 得到权限ID
	 * @return 权限ID
	 */
	public int getPrivilegeId() {
		return privilegeId;
	}

	/**
	 * 设置权限ID
	 * @param privilegeId 权限ID
	 */
	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}

}
