package org.ailab.tem.wim.agentInfo;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：代理商
 */
public class AgentInfo extends BaseDTO {

	// ID
	protected int id;

	// 代理商名称
	protected String name;

	// 代理商链接
	protected String agentLinks;

	// 描述
	protected String description;

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
	 * 得到代理商名称
	 * @return 代理商名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置代理商名称
	 * @param name 代理商名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 得到代理商链接
	 * @return 代理商链接
	 */
	public String getAgentLinks() {
		return agentLinks;
	}

	/**
	 * 设置代理商链接
	 * @param agentLinks 代理商链接
	 */
	public void setAgentLinks(String agentLinks) {
		this.agentLinks = agentLinks;
	}

	/**
	 * 得到描述
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
