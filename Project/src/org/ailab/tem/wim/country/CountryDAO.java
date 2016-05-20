package org.ailab.tem.wim.country;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-04
 * 功能描述：国家和地区业务逻辑
 */
public class CountryDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(CountryDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "country";
	}


    /**
     * 默认的排序方式
     *
     * @return
     */
    protected String getDefaultOrderBy() {
        return "seqInDoc, pinyin";
    }


    /**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from country where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM country";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM country";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM country WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Country dto = new Country();

		dto.setId(res.getInt("id"));
		dto.setTld(getChar(res, "tld"));
		dto.setName_en(res.getString("name_en"));
		dto.setName_zh(res.getString("name_zh"));
		dto.setName_local(res.getString("name_local"));
		dto.setAbbr_zh(res.getString("abbr_zh"));
		dto.setFullName_zh(res.getString("fullName_zh"));
		dto.setFullName_en(res.getString("fullName_en"));
		dto.setTelCode(res.getInt("telCode"));
		dto.setLongitude(res.getString("longitude"));
		dto.setLatitude(res.getString("latitude"));
		dto.setDescription(res.getString("description"));
		dto.setAliases(res.getString("aliases"));
		dto.setContinentId(res.getInt("continentId"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO country (id, tld, name_en, name_zh, name_local, abbr_zh, fullName_zh, fullName_en, telCode, longitude, latitude, description, aliases, continentId)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Country dto = (Country) baseDTO;

		paramList.add(dto.getId());
		paramList.add(String.valueOf(dto.getTld()));
		paramList.add(dto.getName_en());
		paramList.add(dto.getName_zh());
		paramList.add(dto.getName_local());
		paramList.add(dto.getAbbr_zh());
		paramList.add(dto.getFullName_zh());
		paramList.add(dto.getFullName_en());
		paramList.add(dto.getTelCode());
		paramList.add(dto.getLongitude());
		paramList.add(dto.getLatitude());
		paramList.add(dto.getDescription());
		paramList.add(dto.getAliases());
		paramList.add(dto.getContinentId());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE country SET tld = ?, name_en = ?, name_zh = ?, name_local = ?, abbr_zh = ?, fullName_zh = ?, fullName_en = ?, telCode = ?, longitude = ?, latitude = ?, description = ?, aliases = ?, continentId = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Country dto = (Country) baseDTO;

		paramList.add(String.valueOf(dto.getTld()));
		paramList.add(dto.getName_en());
		paramList.add(dto.getName_zh());
		paramList.add(dto.getName_local());
		paramList.add(dto.getAbbr_zh());
		paramList.add(dto.getFullName_zh());
		paramList.add(dto.getFullName_en());
		paramList.add(dto.getTelCode());
		paramList.add(dto.getLongitude());
		paramList.add(dto.getLatitude());
		paramList.add(dto.getDescription());
		paramList.add(dto.getAliases());
		paramList.add(dto.getContinentId());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM country WHERE id = ?";
	}


	/**
	 * 由id得到country
	 * @param id id
	 */
	public Country get(int id) throws SQLException {
        Country country;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        country = (Country) getBySql("select * from country where id = ?", paramList);

        return country;
	}

}
