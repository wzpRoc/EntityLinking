package org.ailab.entityLinking.wim.mention;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：指称
 */
public class Mention extends BaseDTO {
	// ID
	protected int id;

	// 文档ID
	protected int docId;

	// 文档内部序号
	protected int seq;

	// 开始位置
	protected int startIdx;

	// 结束位置
	protected int endIdx;

	// 字符串
	protected String name;

	// 实体ID
	protected int entityId;

	// 维基标题
	protected String wikiTitle;

	// 初始权重
	protected float initialWeightInDoc;

	// tf
	protected double tf;

	// idf
	protected double idf;

	// tfidf
	protected double tfidf;

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
	 * 得到文档内部序号
	 * @return 文档内部序号
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * 设置文档内部序号
	 * @param seq 文档内部序号
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * 得到开始位置
	 * @return 开始位置
	 */
	public int getStartIdx() {
		return startIdx;
	}

	/**
	 * 设置开始位置
	 * @param startIdx 开始位置
	 */
	public void setStartIdx(int startIdx) {
		this.startIdx = startIdx;
	}

	/**
	 * 得到结束位置
	 * @return 结束位置
	 */
	public int getEndIdx() {
		return endIdx;
	}

	/**
	 * 设置结束位置
	 * @param endIdx 结束位置
	 */
	public void setEndIdx(int endIdx) {
		this.endIdx = endIdx;
	}

	/**
	 * 得到字符串
	 * @return 字符串
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置字符串
	 * @param name 字符串
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 得到维基标题
	 * @return 维基标题
	 */
	public String getWikiTitle() {
		return wikiTitle;
	}

	/**
	 * 设置维基标题
	 * @param wikiTitle 维基标题
	 */
	public void setWikiTitle(String wikiTitle) {
		this.wikiTitle = wikiTitle;
	}

	/**
	 * 得到初始权重
	 * @return 初始权重
	 */
	public float getInitialWeightInDoc() {
		return initialWeightInDoc;
	}

	/**
	 * 设置初始权重
	 * @param initialWeightInDoc 初始权重
	 */
	public void setInitialWeightInDoc(float initialWeightInDoc) {
		this.initialWeightInDoc = initialWeightInDoc;
	}

	/**
	 * 得到tf
	 * @return tf
	 */
	public double getTf() {
		return tf;
	}

	/**
	 * 设置tf
	 * @param tf tf
	 */
	public void setTf(double tf) {
		this.tf = tf;
	}

	/**
	 * 得到idf
	 * @return idf
	 */
	public double getIdf() {
		return idf;
	}

	/**
	 * 设置idf
	 * @param idf idf
	 */
	public void setIdf(double idf) {
		this.idf = idf;
	}

	/**
	 * 得到tfidf
	 * @return tfidf
	 */
	public double getTfidf() {
		return tfidf;
	}

	/**
	 * 设置tfidf
	 * @param tfidf tfidf
	 */
	public void setTfidf(double tfidf) {
		this.tfidf = tfidf;
	}

}
