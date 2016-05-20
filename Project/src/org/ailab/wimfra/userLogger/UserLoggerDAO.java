package org.ailab.wimfra.userLogger;


import org.ailab.wimfra.core.BaseDAO;
import org.ailab.wimfra.core.BaseDTO;
import org.apache.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2013-11-25
 * 功能描述：用户日志业务逻辑
 */
public class UserLoggerDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(UserLoggerDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "userLogger";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from userLogger where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM userLogger";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM userLogger";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM userLogger WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		UserLogger dto = new UserLogger();

		dto.setId(res.getInt("id"));
		dto.setUserId(res.getInt("userId"));
		dto.setLogLevel(res.getInt("logLevel"));
		dto.setOpDate(res.getString("opDate"));
		dto.setStartTime(res.getString("startTime"));
		dto.setDuration(res.getInt("duration"));
		dto.setClassName(res.getString("className"));
		dto.setMethod(res.getString("method"));
		dto.setOpName(res.getString("opName"));
		dto.setResult(res.getInt("result"));
		dto.setIp(res.getString("ip"));
		dto.setUrl(res.getString("url"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO userLogger (id, userId, logLevel, opDate, startTime, duration, className, method, opName, result, ip, url)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 * @param vector
	 * @param BaseDTO
	 */
	protected void setParametersForInsert(List<Object> vector, BaseDTO BaseDTO) {
		UserLogger dto = (UserLogger) BaseDTO;

		vector.add(dto.getId());
		vector.add(dto.getUserId());
		vector.add(dto.getLogLevel());
		vector.add(dto.getOpDate());
		vector.add(dto.getStartTime());
		vector.add(dto.getDuration());
		vector.add(dto.getClassName());
		vector.add(dto.getMethod());
		vector.add(dto.getOpName());
		vector.add(dto.getResult());
		vector.add(dto.getIp());
		vector.add(dto.getUrl());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE userLogger SET userId = ?, logLevel = ?, opDate = ?, startTime = ?, duration = ?, className = ?, method = ?, opName = ?, result = ?, ip = ?, url = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 * @param vector
	 * @param BaseDTO
	 */
	protected void setParametersForUpdate(List<Object> vector, BaseDTO BaseDTO) {
		UserLogger dto = (UserLogger) BaseDTO;

		vector.add(dto.getUserId());
		vector.add(dto.getLogLevel());
		vector.add(dto.getOpDate());
		vector.add(dto.getStartTime());
		vector.add(dto.getDuration());
		vector.add(dto.getClassName());
		vector.add(dto.getMethod());
		vector.add(dto.getOpName());
		vector.add(dto.getResult());
		vector.add(dto.getIp());
		vector.add(dto.getUrl());

		vector.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM userLogger WHERE id = ?";
	}


	/**
	 * 由id得到userLogger
	 * @param id id
	 */
	public UserLogger get(int id) throws SQLException {
        UserLogger userLogger;
        Vector<Object> vector = new Vector<Object>();
        vector.add(id);
        userLogger = (UserLogger) getBySql("select * from userLogger where id = ?", vector);

        return userLogger;
	}

}
