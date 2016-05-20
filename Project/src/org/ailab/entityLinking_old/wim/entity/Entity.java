package org.ailab.entityLinking_old.wim.entity;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：实体
 */
public class Entity extends BaseDTO {
    public static final Entity NIL;

    static {
        NIL = new Entity();
        NIL.setId(0);
        NIL.setAbst("");
        NIL.setTitle("NIL");
    }

	// id
	protected int id;
	protected int wikiPageId;

	// name
	protected String title;
	protected String titleNormalized;

	// abst
	protected String abst;

	// factCount
	protected int factCount;

    protected int wikiPageLinkCount;

	/**
	 * 得到id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 得到name
	 * @return name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置name
	 * @param title name
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 得到abst
	 * @return abst
	 */
	public String getAbst() {
		return abst;
	}

	/**
	 * 设置abst
	 * @param abst abst
	 */
	public void setAbst(String abst) {
		this.abst = abst;
	}

	/**
	 * 得到factCount
	 * @return factCount
	 */
	public int getFactCount() {
		return factCount;
	}

	/**
	 * 设置factCount
	 * @param factCount factCount
	 */
	public void setFactCount(int factCount) {
		this.factCount = factCount;
	}

    @Override
    public String toString() {
        return title+"["+id+"]";
    }

    public int getWikiPageId() {
        return wikiPageId;
    }

    public void setWikiPageId(int wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    public int getWikiPageLinkCount() {
        return wikiPageLinkCount;
    }

    public void setWikiPageLinkCount(int wikiPageLinkCount) {
        this.wikiPageLinkCount = wikiPageLinkCount;
    }

    public boolean isNIL() {
        return this.id == NIL.id;
    }

    public String getTitleNormalized() {
        return titleNormalized;
    }

    public void setTitleNormalized(String titleNormalized) {
        this.titleNormalized = titleNormalized;
    }
}
