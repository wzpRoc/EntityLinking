package org.ailab.wimfra.core;

import org.apache.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2011-09-06
 * 功能描述：数据存取对象（查询）
 */
public abstract class QueryBaseDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(QueryBaseDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
        // todo
	    return null;
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
        // todo
	    return null;
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
        // todo
	    return null;
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
        // todo
	    return null;
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected abstract BaseDTO resultSetToDto(ResultSet res) throws SQLException;

	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
        // todo
	    return null;
	}


	/**
	 * 为insert设置参数
	 * @param vector
	 * @param baseDTO
	 */
	protected void setParametersForInsert(Vector<Object> vector, BaseDTO baseDTO) {
        // todo
	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
        // todo
	    return null;
	}


	/**
	 * 为update设置参数
	 * @param vector
	 * @param baseDTO
	 */
	protected void setParametersForUpdate(Vector<Object> vector, BaseDTO baseDTO) {
        // todo
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
        // todo
	    return null;
	}
}
