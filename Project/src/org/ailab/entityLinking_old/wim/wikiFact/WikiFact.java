package org.ailab.entityLinking_old.wim.wikiFact;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实
 */
public class WikiFact extends BaseDTO {
	// ID
	protected int id;

	// entityId
	protected int entityId;

	// predicateId
	protected int predicateId;

	// objectId
	protected int objectId;

	// entityTitle
	protected String entityTitle;

	// predicateName
	protected String predicateName;

	// objectName
	protected String objectName;

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
	 * 得到subjectId
	 * @return entityId
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * 设置subjectId
	 * @param entityId entityId
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * 得到predicateId
	 * @return predicateId
	 */
	public int getPredicateId() {
		return predicateId;
	}

	/**
	 * 设置predicateId
	 * @param predicateId predicateId
	 */
	public void setPredicateId(int predicateId) {
		this.predicateId = predicateId;
	}

	/**
	 * 得到objectId
	 * @return objectId
	 */
	public int getObjectId() {
		return objectId;
	}

	/**
	 * 设置objectId
	 * @param objectId objectId
	 */
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	/**
	 * 得到subjectName
	 * @return entityTitle
	 */
	public String getEntityTitle() {
		return entityTitle;
	}

	/**
	 * 设置subjectName
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
	 * 得到objectName
	 * @return objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * 设置objectName
	 * @param objectName objectName
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

}
