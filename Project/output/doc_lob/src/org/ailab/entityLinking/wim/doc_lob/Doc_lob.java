package org.ailab.entityLinking.wim.doc_lob;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档大对象
 */
public class Doc_lob extends BaseDTO {
	// ID
	protected int id;

	// 摘要
	protected String abst;

	// 正文
	protected String content;

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
	 * 得到摘要
	 * @return 摘要
	 */
	public String getAbst() {
		return abst;
	}

	/**
	 * 设置摘要
	 * @param abst 摘要
	 */
	public void setAbst(String abst) {
		this.abst = abst;
	}

	/**
	 * 得到正文
	 * @return 正文
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置正文
	 * @param content 正文
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
