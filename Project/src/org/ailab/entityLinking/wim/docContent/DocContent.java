package org.ailab.entityLinking.wim.docContent;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体名称
 */
public class DocContent extends BaseDTO {
	// 文档id
	protected int docId;

	// 文档内容
	protected String content;

	/**
	 * 得到文档id
	 * @return 文档id
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * 设置文档id
	 * @param docId 文档id
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * 得到文档内容
	 * @return 文档内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置文档内容
	 * @param content 文档内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
