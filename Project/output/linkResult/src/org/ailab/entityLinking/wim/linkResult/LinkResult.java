package org.ailab.entityLinking.wim.linkResult;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-18
 * 功能描述：连接结果
 */
public class LinkResult extends BaseDTO {
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

	// 开始位置
	protected long startPosition;

	// 结束位置
	protected long endPosition;

	// 实验ID
	protected String experimentId;

	// 最大概率
	protected int probability0;

	// 最大概率实体ID
	protected int entityId0;

	// 最大概率实体标题
	protected String entityTitle0;

	// 次大概率
	protected double probability1;

	// 次大概率实体ID
	protected int entityId1;

	// 次大概率实体标题
	protected String entityTitle1;

	// 最大与次大概率之差
	protected double probabilityDiff;

	// 是否正确
	protected int correct;

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
	 * 得到实验ID
	 * @return 实验ID
	 */
	public String getExperimentId() {
		return experimentId;
	}

	/**
	 * 设置实验ID
	 * @param experimentId 实验ID
	 */
	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}

	/**
	 * 得到最大概率
	 * @return 最大概率
	 */
	public int getProbability0() {
		return probability0;
	}

	/**
	 * 设置最大概率
	 * @param probability0 最大概率
	 */
	public void setProbability0(int probability0) {
		this.probability0 = probability0;
	}

	/**
	 * 得到最大概率实体ID
	 * @return 最大概率实体ID
	 */
	public int getEntityId0() {
		return entityId0;
	}

	/**
	 * 设置最大概率实体ID
	 * @param entityId0 最大概率实体ID
	 */
	public void setEntityId0(int entityId0) {
		this.entityId0 = entityId0;
	}

	/**
	 * 得到最大概率实体标题
	 * @return 最大概率实体标题
	 */
	public String getEntityTitle0() {
		return entityTitle0;
	}

	/**
	 * 设置最大概率实体标题
	 * @param entityTitle0 最大概率实体标题
	 */
	public void setEntityTitle0(String entityTitle0) {
		this.entityTitle0 = entityTitle0;
	}

	/**
	 * 得到次大概率
	 * @return 次大概率
	 */
	public double getProbability1() {
		return probability1;
	}

	/**
	 * 设置次大概率
	 * @param probability1 次大概率
	 */
	public void setProbability1(double probability1) {
		this.probability1 = probability1;
	}

	/**
	 * 得到次大概率实体ID
	 * @return 次大概率实体ID
	 */
	public int getEntityId1() {
		return entityId1;
	}

	/**
	 * 设置次大概率实体ID
	 * @param entityId1 次大概率实体ID
	 */
	public void setEntityId1(int entityId1) {
		this.entityId1 = entityId1;
	}

	/**
	 * 得到次大概率实体标题
	 * @return 次大概率实体标题
	 */
	public String getEntityTitle1() {
		return entityTitle1;
	}

	/**
	 * 设置次大概率实体标题
	 * @param entityTitle1 次大概率实体标题
	 */
	public void setEntityTitle1(String entityTitle1) {
		this.entityTitle1 = entityTitle1;
	}

	/**
	 * 得到最大与次大概率之差
	 * @return 最大与次大概率之差
	 */
	public double getProbabilityDiff() {
		return probabilityDiff;
	}

	/**
	 * 设置最大与次大概率之差
	 * @param probabilityDiff 最大与次大概率之差
	 */
	public void setProbabilityDiff(double probabilityDiff) {
		this.probabilityDiff = probabilityDiff;
	}

	/**
	 * 得到是否正确
	 * @return 是否正确
	 */
	public int getCorrect() {
		return correct;
	}

	/**
	 * 设置是否正确
	 * @param correct 是否正确
	 */
	public void setCorrect(int correct) {
		this.correct = correct;
	}

}
