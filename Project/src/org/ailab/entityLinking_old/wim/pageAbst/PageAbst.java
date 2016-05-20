package org.ailab.entityLinking_old.wim.pageAbst;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-15
 * 功能描述：维基页面摘要
 */
public class PageAbst extends BaseDTO {
	// page_id
	protected int page_id;

	// page_title
	protected String page_title;

	// page_abst
	protected String page_abst;

	// page_abst_len
	protected int page_abst_len;

	/**
	 * 得到page_id
	 * @return page_id
	 */
	public int getPage_id() {
		return page_id;
	}

	/**
	 * 设置page_id
	 * @param page_id page_id
	 */
	public void setPage_id(int page_id) {
		this.page_id = page_id;
	}

	/**
	 * 得到page_title
	 * @return page_title
	 */
	public String getPage_title() {
		return page_title;
	}

	/**
	 * 设置page_title
	 * @param page_title page_title
	 */
	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}

	/**
	 * 得到page_abst
	 * @return page_abst
	 */
	public String getPage_abst() {
		return page_abst;
	}

	/**
	 * 设置page_abst
	 * @param page_abst page_abst
	 */
	public void setPage_abst(String page_abst) {
		this.page_abst = page_abst;
	}

	/**
	 * 得到page_abst_len
	 * @return page_abst_len
	 */
	public int getPage_abst_len() {
		return page_abst_len;
	}

	/**
	 * 设置page_abst_len
	 * @param page_abst_len page_abst_len
	 */
	public void setPage_abst_len(int page_abst_len) {
		this.page_abst_len = page_abst_len;
	}

}
