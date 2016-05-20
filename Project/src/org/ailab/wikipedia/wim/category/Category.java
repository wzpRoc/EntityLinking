package org.ailab.wikipedia.wim.category;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：类别
 */
public class Category extends BaseDTO {
	// ID
	protected int id;

	// 标题
	protected String title;

	// 描述
	protected String link;

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
	 * 得到标题
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 得到描述
	 * @return 描述
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置描述
	 * @param link 描述
	 */
	public void setLink(String link) {
		this.link = link;
	}

}
