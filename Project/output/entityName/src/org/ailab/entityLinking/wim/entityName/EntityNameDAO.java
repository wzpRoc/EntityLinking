package org.ailab.entityLinking.wim.entityName;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-12
 * 功能描述：实体名称业务逻辑
 */
public class EntityNameDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(EntityNameDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "entityName";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from entityName where entityId = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM entityName";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM entityName";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM entityName WHERE entityId in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		EntityName dto = new EntityName();

		dto.setEntityId(res.getInt("entityId"));
		dto.setEntityTitle(res.getString("entityTitle"));
		dto.setPredicateName(res.getString("predicateName"));
		dto.setEntityName(res.getString("entityName"));
		dto.setPNameToEntity(res.getDouble("pNameToEntity"));
		dto.setPEntityToName(res.getDouble("pEntityToName"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO entityName (entityId, entityTitle, predicateName, entityName, pNameToEntity, pEntityToName)VALUES(?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		EntityName dto = (EntityName) baseDTO;

		paramList.add(dto.getEntityId());
		paramList.add(dto.getEntityTitle());
		paramList.add(dto.getPredicateName());
		paramList.add(dto.getEntityName());
		paramList.add(dto.getPNameToEntity());
		paramList.add(dto.getPEntityToName());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE entityName SET entityTitle = ?, predicateName = ?, entityName = ?, pNameToEntity = ?, pEntityToName = ? WHERE entityId = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		EntityName dto = (EntityName) baseDTO;

		paramList.add(dto.getEntityTitle());
		paramList.add(dto.getPredicateName());
		paramList.add(dto.getEntityName());
		paramList.add(dto.getPNameToEntity());
		paramList.add(dto.getPEntityToName());

		paramList.add(dto.getnull());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM entityName WHERE entityId = ?";
	}


	/**
	 * 由entityId得到entityName
	 * @param id entityId
	 */
	public EntityName get(int id) throws SQLException {
        EntityName entityName;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        entityName = (EntityName) getBySql("select * from entityName where entityId = ?", paramList);

        return entityName;
	}

}
