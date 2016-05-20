package org.ailab.wimfra.core;

import java.util.ArrayList;

/**
 * Action返回的所有结果都放在这个类里头
 * @author playcoin
 *
 */
public class ResultKeeper {
	
	// 右侧关键字列表
	protected ArrayList rightKeywordsList;
	// 右侧关键词标题
	protected String rightKeyowrdsTitle;
	
	// 右侧国家列表
	protected ArrayList rightCountryList;
	// 右侧国家标题
	protected String rightCountryTitle;
	
	// 右侧人物列表
	protected ArrayList rightPersonList;
	// 右侧人物标题
	protected String rightPersonTitle;
	
	// 右侧聚类类别统计
	protected ArrayList rightClusterLists;
	// 右侧聚类各类别的名称
	protected String[] rightClusterTitles;
	
	// 高亮关键词
	private String highlightKeywords;

	/**
	 * @return the rigthKeywordsList
	 */
	public ArrayList getRightKeywordsList() {
		return rightKeywordsList;
	}

	/**
	 * @param rigthKeywordsList the rigthKeywordsList to set
	 */
	public void setRightKeywordsList(ArrayList rightKeywordsList) {
		this.rightKeywordsList = rightKeywordsList;
	}

	/**
	 * @return the rightCountryList
	 */
	public ArrayList getRightCountryList() {
		return rightCountryList;
	}

	/**
	 * @param rightCountryList the rightCountryList to set
	 */
	public void setRightCountryList(ArrayList rightCountryList) {
		this.rightCountryList = rightCountryList;
	}

	/**
	 * @return the rightPersonList
	 */
	public ArrayList getRightPersonList() {
		return rightPersonList;
	}

	/**
	 * @param rightPersonList the rightPersonList to set
	 */
	public void setRightPersonList(ArrayList rightPersonList) {
		this.rightPersonList = rightPersonList;
	}

	/**
	 * @return the highlightKeywords
	 */
	public String getHighlightKeywords() {
		return highlightKeywords;
	}

	/**
	 * @param highlightKeywords the highlightKeywords to set
	 */
	public void setHighlightKeywords(String highlightKeywords) {
		this.highlightKeywords = highlightKeywords;
	}

	/**
	 * @return the rightKeyowrdsTitle
	 */
	public String getRightKeyowrdsTitle() {
		return rightKeyowrdsTitle;
	}

	/**
	 * @param rightKeyowrdsTitle the rightKeyowrdsTitle to set
	 */
	public void setRightKeyowrdsTitle(String rightKeyowrdsTitle) {
		this.rightKeyowrdsTitle = rightKeyowrdsTitle;
	}

	/**
	 * @return the rightCountryTitle
	 */
	public String getRightCountryTitle() {
		return rightCountryTitle;
	}

	/**
	 * @param rightCountryTitle the rightCountryTitle to set
	 */
	public void setRightCountryTitle(String rightCountryTitle) {
		this.rightCountryTitle = rightCountryTitle;
	}

	/**
	 * @return the rightPersonTitle
	 */
	public String getRightPersonTitle() {
		return rightPersonTitle;
	}

	/**
	 * @param rightPersonTitle the rightPersonTitle to set
	 */
	public void setRightPersonTitle(String rightPersonTitle) {
		this.rightPersonTitle = rightPersonTitle;
	}

	/**
	 * @return the rightClusterLists
	 */
	public ArrayList getRightClusterLists() {
		return rightClusterLists;
	}

	/**
	 * @param rightClusterLists the rightClusterLists to set
	 */
	public void setRightClusterLists(ArrayList rightClusterLists) {
		this.rightClusterLists = rightClusterLists;
	}

	/**
	 * @return the rightClusterTitles
	 */
	public String[] getRightClusterTitles() {
		return rightClusterTitles;
	}

	/**
	 * @param rightClusterTitles the rightClusterTitles to set
	 */
	public void setRightClusterTitles(String[] rightClusterTitles) {
		this.rightClusterTitles = rightClusterTitles;
	}
	
}
