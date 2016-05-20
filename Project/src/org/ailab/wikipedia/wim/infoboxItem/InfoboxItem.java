package org.ailab.wikipedia.wim.infoboxItem;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：信息框条目
 */
public class InfoboxItem extends BaseDTO {
	// ID
	protected int id;

	// 
	protected int articleId;

	// 
	protected String title;

	// 
	protected String value;

	protected String normalizedValue;


    public InfoboxItem() {
    }

    public InfoboxItem(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public InfoboxItem(String title, String value, String normalizedValue) {
        this.title = title;
        this.value = value;
        this.normalizedValue = normalizedValue;
    }

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
	 * 得到
	 * @return 
	 */
	public int getArticleId() {
		return articleId;
	}

	/**
	 * 设置
	 * @param articleId 
	 */
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	/**
	 * 得到
	 * @return 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置
	 * @param title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 得到
	 * @return 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置
	 * @param value 
	 */
	public void setValue(String value) {
		this.value = value;
	}


    public String getNormalizedValue() {
        return normalizedValue;
    }

    public void setNormalizedValue(String normalizedValue) {
        this.normalizedValue = normalizedValue;
    }
}
