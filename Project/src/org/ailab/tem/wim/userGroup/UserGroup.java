package org.ailab.tem.wim.userGroup;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组
 */
public class UserGroup extends BaseDTO {
	// 用户组ID
	protected int id;

	// 名称
	protected String name;

	// 类别
	protected int category;

	// 用户数
	protected int userCount;

	// 描述
	protected String description;

	/**
	 * 得到用户组ID
	 * @return 用户组ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置用户组ID
	 * @param id 用户组ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 得到名称
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 得到类别
	 * @return 类别
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * 设置类别
	 * @param category 类别
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * 得到用户数
	 * @return 用户数
	 */
	public int getUserCount() {
		return userCount;
	}

	/**
	 * 设置用户数
	 * @param userCount 用户数
	 */
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	/**
	 * 得到描述
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
