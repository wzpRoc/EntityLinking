package org.ailab.tem.wim.aboutUs;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：关于我们
 */
public class AboutUs extends BaseDTO {
	// ID
	protected int id;

	// 类别ID
	protected int categoryId;

	// 类别名称
	protected String categoryName;

	// 内容
	protected String content;

	// 描述
	protected String description;

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
	 * 得到类别ID
	 * @return 类别ID
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * 设置类别ID
	 * @param categoryId 类别ID
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 得到类别名称
	 * @return 类别名称
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * 设置类别名称
	 * @param categoryName 类别名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * 得到内容
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * @param content 内容
	 */
	public void setContent(String content) {
		this.content = content;
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
