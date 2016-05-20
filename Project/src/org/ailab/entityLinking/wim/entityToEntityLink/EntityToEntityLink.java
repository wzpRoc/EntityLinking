package org.ailab.entityLinking.wim.entityToEntityLink;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体到实体的链接
 */
public class EntityToEntityLink extends BaseDTO {
	// 源实体ID
	protected int fromEntityId;

	// 目的实体ID
	protected int toEntityId;

	// 目的实体维基标题
//	protected String toWikiTitle;

	// 计数
//	protected int cnt;

	/**
	 * 得到源实体ID
	 * @return 源实体ID
	 */
	public int getFromEntityId() {
		return fromEntityId;
	}

	/**
	 * 设置源实体ID
	 * @param fromEntityId 源实体ID
	 */
	public void setFromEntityId(int fromEntityId) {
		this.fromEntityId = fromEntityId;
	}

	/**
	 * 得到目的实体ID
	 * @return 目的实体ID
	 */
	public int getToEntityId() {
		return toEntityId;
	}

	/**
	 * 设置目的实体ID
	 * @param toEntityId 目的实体ID
	 */
	public void setToEntityId(int toEntityId) {
		this.toEntityId = toEntityId;
	}

	/**
	 * 得到目的实体维基标题
	 * @return 目的实体维基标题
	 */
//	public String getToWikiTitle() {
//		return toWikiTitle;
//	}

	/**
	 * 设置目的实体维基标题
	 * @param toWikiTitle 目的实体维基标题
	 */
//	public void setToWikiTitle(String toWikiTitle) {
//		this.toWikiTitle = toWikiTitle;
//	}

	/**
	 * 得到计数
	 * @return 计数
	 */
//	public int getCnt() {
//		return cnt;
//	}

	/**
	 * 设置计数
	 * @param cnt 计数
	 */
//	public void setCnt(int cnt) {
//		this.cnt = cnt;
//	}

}
