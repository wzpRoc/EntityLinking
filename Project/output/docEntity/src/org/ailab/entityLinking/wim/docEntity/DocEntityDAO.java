package org.ailab.entityLinking.wim.docEntity;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档实体业务逻辑
 */
public class DocEntityDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(DocEntityDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "docEntity";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from docEntity where docId = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM docEntity";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM docEntity";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM docEntity WHERE docId in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		DocEntity dto = new DocEntity();

		dto.setDocId(res.getInt("docId"));
		dto.setDate(res.getString("date"));
		dto.setEntityType(getChar(res, "entityType"));
		dto.setEntityId(res.getInt("entityId"));
		dto.setEntityName(res.getString("entityName"));
		dto.setEntityValue(res.getString("entityValue"));
		dto.setField(getChar(res, "field"));
		dto.setStartPosition(res.getLong("startPosition"));
		dto.setEndPosition(res.getLong("endPosition"));
		dto.setCountryId(res.getInt("countryId"));
		dto.setUpdateTime(res.getString("updateTime"));
		dto.setUpdaterName(res.getString("updaterName"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO docEntity (docId, date, entityType, entityId, entityName, entityValue, field, startPosition, endPosition, countryId, updateTime, updaterName)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		DocEntity dto = (DocEntity) baseDTO;

		paramList.add(dto.getDocId());
		paramList.add(dto.getDate());
		paramList.add(String.valueOf(dto.getEntityType()));
paramList.add(dto.getEntityType());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getEntityName());
		paramList.add(dto.getEntityValue());
		paramList.add(String.valueOf(dto.getField()));
paramList.add(dto.getField());
		paramList.add(dto.getStartPosition());
		paramList.add(dto.getEndPosition());
		paramList.add(dto.getCountryId());
		paramList.add(dto.getUpdateTime());
		paramList.add(dto.getUpdaterName());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE docEntity SET date = ?, entityType = ?, entityId = ?, entityName = ?, entityValue = ?, field = ?, startPosition = ?, endPosition = ?, countryId = ?, updateTime = ?, updaterName = ? WHERE docId = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		DocEntity dto = (DocEntity) baseDTO;

		paramList.add(dto.getDate());
		paramList.add(String.valueOf(dto.getEntityType()));
paramList.add(dto.getEntityType());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getEntityName());
		paramList.add(dto.getEntityValue());
		paramList.add(String.valueOf(dto.getField()));
paramList.add(dto.getField());
		paramList.add(dto.getStartPosition());
		paramList.add(dto.getEndPosition());
		paramList.add(dto.getCountryId());
		paramList.add(dto.getUpdateTime());
		paramList.add(dto.getUpdaterName());

		paramList.add(dto.getnull());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM docEntity WHERE docId = ?";
	}


	/**
	 * 由docId得到docEntity
	 * @param id docId
	 */
	public DocEntity get(int id) throws SQLException {
        DocEntity docEntity;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        docEntity = (DocEntity) getBySql("select * from docEntity where docId = ?", paramList);

        return docEntity;
	}

}
