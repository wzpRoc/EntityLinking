package org.ailab.entityLinking_old.wim.entityName;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-12
 * 功能描述：实体名称
 */
public class EntityName extends BaseDTO {
	// entityId
	protected int entityId;

	// entityTitle
	protected String entityTitle;

	// predicateName
	protected String predicateName;

	// entityName
	protected String entityName;

	// pNameToEntity
	protected double pNameToEntity;

	// pEntityToName
	protected double pEntityToName;

    // entityType
	protected String entityType;

	protected String tsTag;

    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDoesntDeclareCloneNotSupportedException"})
    public EntityName clone() {
        EntityName another = new EntityName();

        another.entityId            = this.entityId      ;
        another.entityTitle         = this.entityTitle   ;
        another.predicateName       = this.predicateName ;
        another.entityName          = this.entityName    ;
        another.pNameToEntity       = this.pNameToEntity ;
        another.pEntityToName       = this.pEntityToName ;
        another.entityType          = this.entityType    ;
        another.tsTag               = this.tsTag         ;

        return another;
    }

	/**
	 * 得到entityId
	 * @return entityId
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * 设置entityId
	 * @param entityId entityId
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * 得到entityTitle
	 * @return entityTitle
	 */
	public String getEntityTitle() {
		return entityTitle;
	}

	/**
	 * 设置entityTitle
	 * @param entityTitle entityTitle
	 */
	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}

	/**
	 * 得到predicateName
	 * @return predicateName
	 */
	public String getPredicateName() {
		return predicateName;
	}

	/**
	 * 设置predicateName
	 * @param predicateName predicateName
	 */
	public void setPredicateName(String predicateName) {
		this.predicateName = predicateName;
	}

	/**
	 * 得到entityName
	 * @return entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * 设置entityName
	 * @param entityName entityName
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * 得到pNameToEntity
	 * @return pNameToEntity
	 */
	public double getPNameToEntity() {
		return pNameToEntity;
	}

	/**
	 * 设置pNameToEntity
	 * @param pNameToEntity pNameToEntity
	 */
	public void setPNameToEntity(double pNameToEntity) {
		this.pNameToEntity = pNameToEntity;
	}

	/**
	 * 得到pEntityToName
	 * @return pEntityToName
	 */
	public double getPEntityToName() {
		return pEntityToName;
	}

	/**
	 * 设置pEntityToName
	 * @param pEntityToName pEntityToName
	 */
	public void setPEntityToName(double pEntityToName) {
		this.pEntityToName = pEntityToName;
	}

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getTsTag() {
        return tsTag;
    }

    public void setTsTag(String tsTag) {
        this.tsTag = tsTag;
    }
}
