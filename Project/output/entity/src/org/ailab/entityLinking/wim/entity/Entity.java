package org.ailab.entityLinking.wim.entity;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：实体
 */
public class Entity extends BaseDTO {
	// id
	protected int id;

	// name
	protected String name;

	// abst
	protected String abst;

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

}
