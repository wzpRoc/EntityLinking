package %packagePrefix%.%tableName%;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: %user%
 * 创建日期: %date%
 * 功能描述：%tableComment%业务逻辑
 */
public class %tableName_upInitial%DAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(%tableName_upInitial%DAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "%tableName%";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from %tableName% where %keyFieldName% = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM %tableName%";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM %tableName%";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM %tableName% WHERE %keyFieldName% in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		%tableName_upInitial% dto = new %tableName_upInitial%();

		%resultSetToDto%

		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "%sql_insert%";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		%tableName_upInitial% dto = (%tableName_upInitial%) baseDTO;

		%insert_para%
	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "%sql_update%";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		%tableName_upInitial% dto = (%tableName_upInitial%) baseDTO;

		%update_para%
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM %tableName% WHERE %keyFieldName% = ?";
	}


	/**
	 * 由%keyFieldName%得到%tableName%
	 * @param id %keyFieldName%
	 */
	public %tableName_upInitial% get(int id) throws SQLException {
        %tableName_upInitial% %tableName%;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        %tableName% = (%tableName_upInitial%) getBySql("select * from %tableName% where %keyFieldName% = ?", paramList);

        return %tableName%;
	}

}
