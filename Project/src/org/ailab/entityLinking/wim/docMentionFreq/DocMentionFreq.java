package org.ailab.entityLinking.wim.docMentionFreq;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：实体名称
 */
public class DocMentionFreq extends BaseDTO {
	// 命名实体指称名称
	protected String mentionName;

	// 在语料库中出现的次数
	protected int freq;

	// 在语料库中的idf
	protected double idf;

	/**
	 * 得到命名实体指称名称
	 * @return 命名实体指称名称
	 */
	public String getMentionName() {
		return mentionName;
	}

	/**
	 * 设置命名实体指称名称
	 * @param mentionName 命名实体指称名称
	 */
	public void setMentionName(String mentionName) {
		this.mentionName = mentionName;
	}

	/**
	 * 得到在语料库中出现的次数
	 * @return 在语料库中出现的次数
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * 设置在语料库中出现的次数
	 * @param freq 在语料库中出现的次数
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * 得到在语料库中的idf
	 * @return 在语料库中的idf
	 */
	public double getIdf() {
		return idf;
	}

	/**
	 * 设置在语料库中的idf
	 * @param idf 在语料库中的idf
	 */
	public void setIdf(double idf) {
		this.idf = idf;
	}

}
