package org.ailab.tem.wim.privilege;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-06-21
 * 功能描述：权限表
 */
public class Privilege extends BaseDTO {
	// ID
	protected int id;

	// 权限描述
	protected String description;

    // 是否在组内
    protected boolean isInGroup;

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
	 * 得到权限描述
	 * @return 权限描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置权限描述
	 * @param description 权限描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

    public boolean isInGroup() {
        return isInGroup;
    }

    public void setInGroup(boolean inGroup) {
        isInGroup = inGroup;
    }
}
