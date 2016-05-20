package org.ailab.entityLinking.wim.docWordFreq;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：新闻数据中词出现的频次
 */
public class DocWordFreq extends BaseDTO {
	// 词的名称
	protected String wordName;

	// 文档中出现的次数
	protected int freq;

	// 在语料库中的idf
	protected double idf;

	/**
	 * 得到词的名称
	 * @return 词的名称
	 */
	public String getWordName() {
		return wordName;
	}

	/**
	 * 设置词的名称
	 * @param wordName 词的名称
	 */
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	/**
	 * 得到文档中出现的次数
	 * @return 文档中出现的次数
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * 设置文档中出现的次数
	 * @param freq 文档中出现的次数
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
