package org.ailab.wikipedia.wim.article;

import org.ailab.wikipedia.wim.category.Category;
import org.ailab.wikipedia.wim.infobox.Infobox;
import org.ailab.wimfra.core.BaseDTO;

import java.util.List;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：文章
 */
public class Article extends BaseDTO {
	// ID
	protected int id;

	// 标题
	protected String title;
	protected String normalizedTitle;

	// 摘要
	protected String abst;

	// LINK
	protected String link;

	// LINK
	protected String photoLink;

    protected Infobox infobox;
    protected List<Category> categoryList;

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
	 * 得到LINK
	 * @return LINK
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置LINK
	 * @param link LINK
	 */
	public void setLink(String link) {
		this.link = link;
	}


    public Infobox getInfobox() {
        return infobox;
    }

    public String getInfoboxItemValue(String infoboxItemTitle) {
        if(infobox == null) {
            return null;
        }
        return infobox.get(infoboxItemTitle);
    }

    public void setInfobox(Infobox infobox) {
        this.infobox = infobox;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getNormalizedTitle() {
        return normalizedTitle;
    }

    public void setNormalizedTitle(String normalizedTitle) {
        this.normalizedTitle = normalizedTitle;
    }
}
