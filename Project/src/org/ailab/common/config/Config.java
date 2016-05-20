package org.ailab.common.config;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2012-10-13
 * 功能描述：配置
 */
public class Config extends BaseDTO {
	// ID
	protected int id;

	// 模块
	protected String module;

	// 名称
	protected String name;

	// 值
	protected String value;

	// 描述
	protected String description;

    public Config() {
    }

    public Config(String module, String name, String value) {
        this.module = module;
        this.name = name;
        this.value = value;
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
	 * 得到模块
	 * @return 模块
	 */
	public String getModule() {
		return module;
	}

	/**
	 * 设置模块
	 * @param module 模块
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * 得到名称
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 得到值
	 * @return 值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置值
	 * @param value 值
	 */
	public void setValue(String value) {
		this.value = value;
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
