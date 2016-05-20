package org.ailab.common.country;

import org.ailab.wimfra.core.BaseDTO;
import org.ailab.wimfra.core.IValueAndLabel;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-04
 * 功能描述：国家和地区
 */
public class Country extends BaseDTO implements IValueAndLabel {
	// ID
	protected int id;

	// 顶级域名国家代码
	protected char tld;

	// 名称（英文）
	protected String name_en;

	// 名称（中文简体）
	protected String name_zh;

	// 当地名称
	protected String name_local;

	// 中文简称
	protected String abbr_zh;

	// 中文全称
	protected String fullName_zh;

	// 英文全称
	protected String fullName_en;

	// 电话代码
	protected int telCode;

	// 经度
	protected String longitude;

	// 纬度
	protected String latitude;

	// 描述
	protected String description;

	// 别名
	protected String aliases;

	// 洲ID
	protected int continentId;

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
	 * 得到顶级域名国家代码
	 * @return 顶级域名国家代码
	 */
	public char getTld() {
		return tld;
	}

	/**
	 * 设置顶级域名国家代码
	 * @param tld 顶级域名国家代码
	 */
	public void setTld(char tld) {
		this.tld = tld;
	}

	/**
	 * 得到名称（英文）
	 * @return 名称（英文）
	 */
	public String getName_en() {
		return name_en;
	}

	/**
	 * 设置名称（英文）
	 * @param name_en 名称（英文）
	 */
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	/**
	 * 得到名称（中文简体）
	 * @return 名称（中文简体）
	 */
	public String getName_zh() {
		return name_zh;
	}

	/**
	 * 设置名称（中文简体）
	 * @param name_zh 名称（中文简体）
	 */
	public void setName_zh(String name_zh) {
		this.name_zh = name_zh;
	}

	/**
	 * 得到当地名称
	 * @return 当地名称
	 */
	public String getName_local() {
		return name_local;
	}

	/**
	 * 设置当地名称
	 * @param name_local 当地名称
	 */
	public void setName_local(String name_local) {
		this.name_local = name_local;
	}

	/**
	 * 得到中文简称
	 * @return 中文简称
	 */
	public String getAbbr_zh() {
		return abbr_zh;
	}

	/**
	 * 设置中文简称
	 * @param abbr_zh 中文简称
	 */
	public void setAbbr_zh(String abbr_zh) {
		this.abbr_zh = abbr_zh;
	}

	/**
	 * 得到中文全称
	 * @return 中文全称
	 */
	public String getFullName_zh() {
		return fullName_zh;
	}

	/**
	 * 设置中文全称
	 * @param fullName_zh 中文全称
	 */
	public void setFullName_zh(String fullName_zh) {
		this.fullName_zh = fullName_zh;
	}

	/**
	 * 得到英文全称
	 * @return 英文全称
	 */
	public String getFullName_en() {
		return fullName_en;
	}

	/**
	 * 设置英文全称
	 * @param fullName_en 英文全称
	 */
	public void setFullName_en(String fullName_en) {
		this.fullName_en = fullName_en;
	}

	/**
	 * 得到电话代码
	 * @return 电话代码
	 */
	public int getTelCode() {
		return telCode;
	}

	/**
	 * 设置电话代码
	 * @param telCode 电话代码
	 */
	public void setTelCode(int telCode) {
		this.telCode = telCode;
	}

	/**
	 * 得到经度
	 * @return 经度
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 设置经度
	 * @param longitude 经度
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 得到纬度
	 * @return 纬度
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * 设置纬度
	 * @param latitude 纬度
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
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

	/**
	 * 得到别名
	 * @return 别名
	 */
	public String getAliases() {
		return aliases;
	}

	/**
	 * 设置别名
	 * @param aliases 别名
	 */
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	/**
	 * 得到洲ID
	 * @return 洲ID
	 */
	public int getContinentId() {
		return continentId;
	}

	/**
	 * 设置洲ID
	 * @param continentId 洲ID
	 */
	public void setContinentId(int continentId) {
		this.continentId = continentId;
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
