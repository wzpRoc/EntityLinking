package org.ailab.entityLinking.wim.doc;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-18
 * 功能描述：文档
 */
public class Doc extends BaseDTO {
	// ID
	protected int id;

	// 标题
	protected String title;

	// 内容
	protected String content;

	// token列表
	protected String tokens;

	// URL
	protected String url;

	// 训练/测试标记
	protected String toeTag;

	// 发布日期
	protected String pubDate;

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
	 * 得到标题
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 得到内容
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * @param content 内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 得到token列表
	 * @return token列表
	 */
	public String getTokens() {
		return tokens;
	}

	/**
	 * 设置token列表
	 * @param tokens token列表
	 */
	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	/**
	 * 得到URL
	 * @return URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置URL
	 * @param url URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 得到训练/测试标记
	 * @return 训练/测试标记
	 */
	public String getToeTag() {
		return toeTag;
	}

	/**
	 * 设置训练/测试标记
	 * @param toeTag 训练/测试标记
	 */
	public void setToeTag(String toeTag) {
		this.toeTag = toeTag;
	}

	/**
	 * 得到发布日期
	 * @return 发布日期
	 */
	public String getPubDate() {
		return pubDate;
	}

	/**
	 * 设置发布日期
	 * @param pubDate 发布日期
	 */
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

}
