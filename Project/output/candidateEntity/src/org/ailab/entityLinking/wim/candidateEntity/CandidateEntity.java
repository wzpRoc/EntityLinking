package org.ailab.entityLinking.wim.candidateEntity;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：指称的候选实体
 */
public class CandidateEntity extends BaseDTO {
	// ID
	protected int id;

	// 文档ID
	protected int docId;

	// 文档内部指称序号
	protected int seqInDoc;

	// 指称ID
	protected int mentionId;

	// 实体名称
	protected String mentionName;

	// 候选实体ID
	protected int entityId;

	// 候选实体维基标题
	protected String wikiTitle;

	// 指称到实体的概率
	protected double probOfMentionToEntity;

	// 指称名字到实体的概率
	protected double probOfNameToEntity;

	// 指称到实体的概率排序
	protected int rankByProbOfNameToEntity;

	// 指称名字到实体的概率排序
	protected int rankByProbOfMentionToEntity;

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
	 * 得到文档内部指称序号
	 * @return 文档内部指称序号
	 */
	public int getSeqInDoc() {
		return seqInDoc;
	}

	/**
	 * 设置文档内部指称序号
	 * @param seqInDoc 文档内部指称序号
	 */
	public void setSeqInDoc(int seqInDoc) {
		this.seqInDoc = seqInDoc;
	}

	/**
	 * 得到指称ID
	 * @return 指称ID
	 */
	public int getMentionId() {
		return mentionId;
	}

	/**
	 * 设置指称ID
	 * @param mentionId 指称ID
	 */
	public void setMentionId(int mentionId) {
		this.mentionId = mentionId;
	}

	/**
	 * 得到实体名称
	 * @return 实体名称
	 */
	public String getMentionName() {
		return mentionName;
	}

	/**
	 * 设置实体名称
	 * @param mentionName 实体名称
	 */
	public void setMentionName(String mentionName) {
		this.mentionName = mentionName;
	}

	/**
	 * 得到候选实体ID
	 * @return 候选实体ID
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * 设置候选实体ID
	 * @param entityId 候选实体ID
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * 得到候选实体维基标题
	 * @return 候选实体维基标题
	 */
	public String getWikiTitle() {
		return wikiTitle;
	}

	/**
	 * 设置候选实体维基标题
	 * @param wikiTitle 候选实体维基标题
	 */
	public void setWikiTitle(String wikiTitle) {
		this.wikiTitle = wikiTitle;
	}

	/**
	 * 得到指称到实体的概率
	 * @return 指称到实体的概率
	 */
	public double getProbOfMentionToEntity() {
		return probOfMentionToEntity;
	}

	/**
	 * 设置指称到实体的概率
	 * @param probOfMentionToEntity 指称到实体的概率
	 */
	public void setProbOfMentionToEntity(double probOfMentionToEntity) {
		this.probOfMentionToEntity = probOfMentionToEntity;
	}

	/**
	 * 得到指称名字到实体的概率
	 * @return 指称名字到实体的概率
	 */
	public double getProbOfNameToEntity() {
		return probOfNameToEntity;
	}

	/**
	 * 设置指称名字到实体的概率
	 * @param probOfNameToEntity 指称名字到实体的概率
	 */
	public void setProbOfNameToEntity(double probOfNameToEntity) {
		this.probOfNameToEntity = probOfNameToEntity;
	}

	/**
	 * 得到指称到实体的概率排序
	 * @return 指称到实体的概率排序
	 */
	public int getRankByProbOfNameToEntity() {
		return rankByProbOfNameToEntity;
	}

	/**
	 * 设置指称到实体的概率排序
	 * @param rankByProbOfNameToEntity 指称到实体的概率排序
	 */
	public void setRankByProbOfNameToEntity(int rankByProbOfNameToEntity) {
		this.rankByProbOfNameToEntity = rankByProbOfNameToEntity;
	}

	/**
	 * 得到指称名字到实体的概率排序
	 * @return 指称名字到实体的概率排序
	 */
	public int getRankByProbOfMentionToEntity() {
		return rankByProbOfMentionToEntity;
	}

	/**
	 * 设置指称名字到实体的概率排序
	 * @param rankByProbOfMentionToEntity 指称名字到实体的概率排序
	 */
	public void setRankByProbOfMentionToEntity(int rankByProbOfMentionToEntity) {
		this.rankByProbOfMentionToEntity = rankByProbOfMentionToEntity;
	}

}
