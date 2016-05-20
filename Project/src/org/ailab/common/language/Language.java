package org.ailab.common.language;

import org.ailab.wimfra.core.BaseDTO;
import org.ailab.wimfra.core.IValueAndLabel;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-04
 * 功能描述：语种
 */
public class Language extends BaseDTO implements IValueAndLabel{
	// ID
	protected int id;

	// 英文名
	protected String name_en;

	// 中文名
	protected String name_zh;

	// 当地名
	protected String name_local;

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
	 * 得到英文名
	 * @return 英文名
	 */
	public String getName_en() {
		return name_en;
	}

	/**
	 * 设置英文名
	 * @param name_en 英文名
	 */
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	/**
	 * 得到中文名
	 * @return 中文名
	 */
	public String getName_zh() {
		return name_zh;
	}

	/**
	 * 设置中文名
	 * @param name_zh 中文名
	 */
	public void setName_zh(String name_zh) {
		this.name_zh = name_zh;
	}

	/**
	 * 得到当地名
	 * @return 当地名
	 */
	public String getName_local() {
		return name_local;
	}

	/**
	 * 设置当地名
	 * @param name_local 当地名
	 */
	public void setName_local(String name_local) {
		this.name_local = name_local;
	}

    @Override
    public String getValue() {
        return String.valueOf(id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLabel() {
        return name_zh;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
