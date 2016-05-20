package org.ailab.entityLinking.wim.docEntity;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档实体
 */
public class DocEntity extends BaseDTO {
	// 文档ID
	protected int docId;

	// 日期
	protected String date;

	// 实体类型
	protected char entityType;

	// 实体ID
	protected int entityId;

	// 实体名称
	protected String entityName;

	// 实体值
	protected String entityValue;

	// 字段
	protected char field;

	// 开始位置
	protected long startPosition;

	// 结束位置
	protected long endPosition;

	// 国家ID
	protected int countryId;

	// 更新时间
	protected String updateTime;

	// 更新者
	protected String updaterName;

	/**
	 * 得到文档ID
	 * @return 文档ID
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * 设置文档ID
	 * @param docId 文档ID
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * 得到日期
	 * @return 日期
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 设置日期
	 * @param date 日期
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 得到实体类型
	 * @return 实体类型
	 */
	public char getEntityType() {
		return entityType;
	}

	/**
	 * 设置实体类型
	 * @param entityType 实体类型
	 */
	public void setEntityType(char entityType) {
		this.entityType = entityType;
	}

	/**
	 * 得到实体ID
	 * @return 实体ID
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * 设置实体ID
	 * @param entityId 实体ID
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * 得到实体名称
	 * @return 实体名称
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * 设置实体名称
	 * @param entityName 实体名称
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * 得到实体值
	 * @return 实体值
	 */
	public String getEntityValue() {
		return entityValue;
	}

	/**
	 * 设置实体值
	 * @param entityValue 实体值
	 */
	public void setEntityValue(String entityValue) {
		this.entityValue = entityValue;
	}

	/**
	 * 得到字段
	 * @return 字段
	 */
	public char getField() {
		return field;
	}

	/**
	 * 设置字段
	 * @param field 字段
	 */
	public void setField(char field) {
		this.field = field;
	}

	/**
	 * 得到开始位置
	 * @return 开始位置
	 */
	public long getStartPosition() {
		return startPosition;
	}

	/**
	 * 设置开始位置
	 * @param startPosition 开始位置
	 */
	public void setStartPosition(long startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * 得到结束位置
	 * @return 结束位置
	 */
	public long getEndPosition() {
		return endPosition;
	}

	/**
	 * 设置结束位置
	 * @param endPosition 结束位置
	 */
	public void setEndPosition(long endPosition) {
		this.endPosition = endPosition;
	}

	/**
	 * 得到国家ID
	 * @return 国家ID
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * 设置国家ID
	 * @param countryId 国家ID
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	/**
	 * 得到更新时间
	 * @return 更新时间
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置更新时间
	 * @param updateTime 更新时间
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 得到更新者
	 * @return 更新者
	 */
	public String getUpdaterName() {
		return updaterName;
	}

	/**
	 * 设置更新者
	 * @param updaterName 更新者
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

}
