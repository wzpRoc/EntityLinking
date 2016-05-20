package org.ailab.entityLinking.wim.linkResult;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-18
 * 功能描述：连接结果业务逻辑
 */
public class LinkResultDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(LinkResultDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "linkResult";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from linkResult where docId = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM linkResult";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM linkResult";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM linkResult WHERE docId in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		LinkResult dto = new LinkResult();

		dto.setDocId(res.getInt("docId"));
		dto.setDate(res.getString("date"));
		dto.setEntityType(getChar(res, "entityType"));
		dto.setEntityId(res.getInt("entityId"));
		dto.setEntityName(res.getString("entityName"));
		dto.setEntityValue(res.getString("entityValue"));
		dto.setStartPosition(res.getLong("startPosition"));
		dto.setEndPosition(res.getLong("endPosition"));
		dto.setExperimentId(res.getString("experimentId"));
		dto.setProbability0(res.getInt("probability0"));
		dto.setEntityId0(res.getInt("entityId0"));
		dto.setEntityTitle0(res.getString("entityTitle0"));
		dto.setProbability1(res.getDouble("probability1"));
		dto.setEntityId1(res.getInt("entityId1"));
		dto.setEntityTitle1(res.getString("entityTitle1"));
		dto.setProbabilityDiff(res.getDouble("probabilityDiff"));
		dto.setCorrect(res.getInt("correct"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO linkResult (docId, date, entityType, entityId, entityName, entityValue, startPosition, endPosition, experimentId, probability0, entityId0, entityTitle0, probability1, entityId1, entityTitle1, probabilityDiff, correct)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		LinkResult dto = (LinkResult) baseDTO;

		paramList.add(dto.getDocId());
		paramList.add(dto.getDate());
		paramList.add(String.valueOf(dto.getEntityType()));
paramList.add(dto.getEntityType());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getEntityName());
		paramList.add(dto.getEntityValue());
		paramList.add(dto.getStartPosition());
		paramList.add(dto.getEndPosition());
		paramList.add(dto.getExperimentId());
		paramList.add(dto.getProbability0());
		paramList.add(dto.getEntityId0());
		paramList.add(dto.getEntityTitle0());
		paramList.add(dto.getProbability1());
		paramList.add(dto.getEntityId1());
		paramList.add(dto.getEntityTitle1());
		paramList.add(dto.getProbabilityDiff());
		paramList.add(dto.getCorrect());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE linkResult SET date = ?, entityType = ?, entityId = ?, entityName = ?, entityValue = ?, startPosition = ?, endPosition = ?, experimentId = ?, probability0 = ?, entityId0 = ?, entityTitle0 = ?, probability1 = ?, entityId1 = ?, entityTitle1 = ?, probabilityDiff = ?, correct = ? WHERE docId = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		LinkResult dto = (LinkResult) baseDTO;

		paramList.add(dto.getDate());
		paramList.add(String.valueOf(dto.getEntityType()));
paramList.add(dto.getEntityType());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getEntityName());
		paramList.add(dto.getEntityValue());
		paramList.add(dto.getStartPosition());
		paramList.add(dto.getEndPosition());
		paramList.add(dto.getExperimentId());
		paramList.add(dto.getProbability0());
		paramList.add(dto.getEntityId0());
		paramList.add(dto.getEntityTitle0());
		paramList.add(dto.getProbability1());
		paramList.add(dto.getEntityId1());
		paramList.add(dto.getEntityTitle1());
		paramList.add(dto.getProbabilityDiff());
		paramList.add(dto.getCorrect());

		paramList.add(dto.getnull());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM linkResult WHERE docId = ?";
	}


	/**
	 * 由docId得到linkResult
	 * @param id docId
	 */
	public LinkResult get(int id) throws SQLException {
        LinkResult linkResult;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        linkResult = (LinkResult) getBySql("select * from linkResult where docId = ?", paramList);

        return linkResult;
	}

}
