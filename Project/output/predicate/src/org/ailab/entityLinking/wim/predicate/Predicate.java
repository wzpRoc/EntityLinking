package org.ailab.entityLinking.wim.predicate;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：谓词
 */
public class Predicate extends BaseDTO {
	// id
	protected int id;

	// name
	protected String name;

	// name
	protected String normalizedName;

	// factCount
	protected int factCount;

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
	public String getName() {
		return name;
	}

	/**
	 * 设置name
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 得到name
	 * @return name
	 */
	public String getNormalizedName() {
		return normalizedName;
	}

	/**
	 * 设置name
	 * @param normalizedName name
	 */
	public void setNormalizedName(String normalizedName) {
		this.normalizedName = normalizedName;
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

}
