package org.ailab.entityLinking.wim.wikiFact;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实
 */
public class WikiFact extends BaseDTO {
	// ID
	protected int id;

	// subjectId
	protected int subjectId;

	// predicateId
	protected int predicateId;

	// objectId
	protected int objectId;

	// subjectName
	protected String subjectName;

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
	 * @return subjectId
	 */
	public int getSubjectId() {
		return subjectId;
	}

	/**
	 * 设置subjectId
	 * @param subjectId subjectId
	 */
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
	 * @return subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * 设置subjectName
	 * @param subjectName subjectName
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
