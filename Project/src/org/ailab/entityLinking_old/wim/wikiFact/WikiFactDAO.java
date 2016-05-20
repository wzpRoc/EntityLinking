package org.ailab.entityLinking_old.wim.wikiFact;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiFactDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(WikiFactDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "wikiFact";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from wikiFact where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM wikiFact";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM wikiFact";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM wikiFact WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		WikiFact dto = new WikiFact();

		dto.setId(res.getInt("id"));
		dto.setEntityId(res.getInt("entityId"));
		dto.setPredicateId(res.getInt("predicateId"));
		dto.setObjectId(res.getInt("objectId"));
		dto.setEntityTitle(res.getString("entityTitle"));
		dto.setPredicateName(res.getString("predicateName"));
		dto.setObjectName(res.getString("objectName"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO wikiFact (id, entityId, predicateId, objectId, entityTitle, predicateName, objectName)VALUES(?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		WikiFact dto = (WikiFact) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getPredicateId());
		paramList.add(dto.getObjectId());
		paramList.add(dto.getEntityTitle());
		paramList.add(dto.getPredicateName());
		paramList.add(dto.getObjectName());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE wikiFact SET entityId = ?, predicateId = ?, objectId = ?, entityTitle = ?, predicateName = ?, objectName = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		WikiFact dto = (WikiFact) baseDTO;

		paramList.add(dto.getEntityId());
		paramList.add(dto.getPredicateId());
		paramList.add(dto.getObjectId());
		paramList.add(dto.getEntityTitle());
		paramList.add(dto.getPredicateName());
		paramList.add(dto.getObjectName());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM wikiFact WHERE id = ?";
	}


	/**
	 * 由id得到wikiFact
	 * @param id id
	 */
	public WikiFact get(int id) throws SQLException {
        WikiFact wikiFact;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        wikiFact = (WikiFact) getBySql("select * from wikiFact where id = ?", paramList);

        return wikiFact;
	}


    public Set<String> getCategoriesByEntityId(int entityId){
        return getStringSet("SELECT objectName\n" +
                "FROM wikiFact\n" +
                "WHERE entityId=?\n" +
                "AND   predicateName = 'NLPCC_category'", entityId);
    }


}
