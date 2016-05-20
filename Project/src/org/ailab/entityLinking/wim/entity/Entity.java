package org.ailab.entityLinking.wim.entity;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：实体
 */
public class Entity extends BaseDTO {
	// ID
	protected int id;

	// 维基百科页面ID
	protected int wikiPageId;

	// 维基百科标题
	protected String wikiTitle;

	// 摘要
	protected String abst;

	// 出度
	protected int outlinkCount;

	// 入度
	protected int inlinkCount;

	// 流行度
	protected float popularity;

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
	 * 得到维基百科页面ID
	 * @return 维基百科页面ID
	 */
	public int getWikiPageId() {
		return wikiPageId;
	}

	/**
	 * 设置维基百科页面ID
	 * @param wikiPageId 维基百科页面ID
	 */
	public void setWikiPageId(int wikiPageId) {
		this.wikiPageId = wikiPageId;
	}

	/**
	 * 得到维基百科标题
	 * @return 维基百科标题
	 */
	public String getWikiTitle() {
		return wikiTitle;
	}

	/**
	 * 设置维基百科标题
	 * @param wikiTitle 维基百科标题
	 */
	public void setWikiTitle(String wikiTitle) {
		this.wikiTitle = wikiTitle;
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
	 * 得到出度
	 * @return 出度
	 */
	public int getOutlinkCount() {
		return outlinkCount;
	}

	/**
	 * 设置出度
	 * @param outlinkCount 出度
	 */
	public void setOutlinkCount(int outlinkCount) {
		this.outlinkCount = outlinkCount;
	}

	/**
	 * 得到入度
	 * @return 入度
	 */
	public int getInlinkCount() {
		return inlinkCount;
	}

	/**
	 * 设置入度
	 * @param inlinkCount 入度
	 */
	public void setInlinkCount(int inlinkCount) {
		this.inlinkCount = inlinkCount;
	}

	/**
	 * 得到流行度
	 * @return 流行度
	 */
	public float getPopularity() {
		return popularity;
	}

	/**
	 * 设置流行度
	 * @param popularity 流行度
	 */
	public void setPopularity(float popularity) {
		this.popularity = popularity;
	}

}
