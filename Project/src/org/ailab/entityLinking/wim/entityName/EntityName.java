package org.ailab.entityLinking.wim.entityName;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-20
 * 功能描述：实体名称
 */
public class EntityName extends BaseDTO {
	// 实体ID
	protected int entityId;

	// 实体名称
	protected String name;

	// 维基标题
	protected String wikiTitle;

	// 从名称到实体的概率
	protected double probOfNameToEntity;

	// 从实体到名称的概率
	protected double probOfEntityToName;

    // 从名称到实体的概率的排序，从0开始
    protected int seq;

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
	 * 得到实体名称
	 * @return 实体名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置实体名称
	 * @param name 实体名称
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 得到从名称到实体的概率
	 * @return 从名称到实体的概率
	 */
	public double getProbOfNameToEntity() {
		return probOfNameToEntity;
	}

	/**
	 * 设置从名称到实体的概率
	 * @param probOfNameToEntity 从名称到实体的概率
	 */
	public void setProbOfNameToEntity(double probOfNameToEntity) {
		this.probOfNameToEntity = probOfNameToEntity;
	}

	/**
	 * 得到从实体到名称的概率
	 * @return 从实体到名称的概率
	 */
	public double getProbOfEntityToName() {
		return probOfEntityToName;
	}

	/**
	 * 设置从实体到名称的概率
	 * @param probOfEntityToName 从实体到名称的概率
	 */
	public void setProbOfEntityToName(double probOfEntityToName) {
		this.probOfEntityToName = probOfEntityToName;
	}

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getKey() {
        return name+" "+entityId;
    }


}
