package org.ailab.entityLinking.wim.predicate;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：谓词业务逻辑
 */
public class PredicateDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(PredicateDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "predicate";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from predicate where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM predicate";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM predicate";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM predicate WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Predicate dto = new Predicate();

		dto.setId(res.getInt("id"));
		dto.setName(res.getString("name"));
		dto.setNormalizedName(res.getString("normalizedName"));
		dto.setFactCount(res.getInt("factCount"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO predicate (id, name, normalizedName, factCount)VALUES(?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Predicate dto = (Predicate) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getName());
		paramList.add(dto.getNormalizedName());
		paramList.add(dto.getFactCount());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE predicate SET name = ?, normalizedName = ?, factCount = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Predicate dto = (Predicate) baseDTO;

		paramList.add(dto.getName());
		paramList.add(dto.getNormalizedName());
		paramList.add(dto.getFactCount());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM predicate WHERE id = ?";
	}


	/**
	 * 由id得到predicate
	 * @param id id
	 */
	public Predicate get(int id) throws SQLException {
        Predicate predicate;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        predicate = (Predicate) getBySql("select * from predicate where id = ?", paramList);

        return predicate;
	}

}
