package org.ailab.common.language;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-12-04
 * 功能描述：语种业务逻辑
 */
public class LanguageDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(LanguageDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "language";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from language where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM language";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM language";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM language WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Language dto = new Language();

		dto.setId(res.getInt("id"));
		dto.setName_en(res.getString("name_en"));
		dto.setName_zh(res.getString("name_zh"));
		dto.setName_local(res.getString("name_local"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO language (id, name_en, name_zh, name_local)VALUES(?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Language dto = (Language) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getName_en());
		paramList.add(dto.getName_zh());
		paramList.add(dto.getName_local());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE language SET name_en = ?, name_zh = ?, name_local = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Language dto = (Language) baseDTO;

		paramList.add(dto.getName_en());
		paramList.add(dto.getName_zh());
		paramList.add(dto.getName_local());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM language WHERE id = ?";
	}


	/**
	 * 由id得到language
	 * @param id id
	 */
	public Language get(int id) throws SQLException {
        Language language;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        language = (Language) getBySql("select * from language where id = ?", paramList);

        return language;
	}

}
