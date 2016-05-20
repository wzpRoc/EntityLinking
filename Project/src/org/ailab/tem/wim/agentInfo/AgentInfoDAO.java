package org.ailab.tem.wim.agentInfo;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：代理商业务逻辑
 */
public class AgentInfoDAO extends BaseDAO {

	/** 日志工具 */
	private Logger logger = Logger.getLogger(AgentInfoDAO.class);

	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "agentInfo";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from agentInfo where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM agentInfo";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM agentInfo";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM agentInfo WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		AgentInfo dto = new AgentInfo();

		dto.setId(res.getInt("id"));
		dto.setName(res.getString("name"));
		dto.setAgentLinks(res.getString("agentLinks"));
		dto.setDescription(res.getString("description"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO agentInfo (id, name, agentLinks, description)VALUES(?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		AgentInfo dto = (AgentInfo) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getName());
		paramList.add(dto.getAgentLinks());
		paramList.add(dto.getDescription());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE agentInfo SET name = ?, agentLinks = ?, description = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		AgentInfo dto = (AgentInfo) baseDTO;

		paramList.add(dto.getName());
		paramList.add(dto.getAgentLinks());
		paramList.add(dto.getDescription());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM agentInfo WHERE id = ?";
	}


	/**
	 * 由id得到agentInfo
	 * @param id id
	 */
	public AgentInfo get(int id) throws SQLException {
        AgentInfo agentInfo;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        agentInfo = (AgentInfo) getBySql("select * from agentInfo where id = ?", paramList);

        return agentInfo;
	}

}
