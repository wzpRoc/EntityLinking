package org.ailab.entityLinking_old.wim.docEntity;

import org.ailab.tem.DBConfig;
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

    public DocEntityDAO() {
        this.dbConnectConfig = DBConfig.docDB.getName();
    }

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
		dto.setPubdate(res.getString("pubdate"));
		dto.setEntityType(getChar(res, "entityType"));
		dto.setEntityId(res.getInt("entityId"));
		dto.setMention(res.getString("mention"));
		dto.setEntityTitle(res.getString("entityTitle"));
		dto.setField(getChar(res, "field"));
		dto.setStartIdx(res.getLong("startIdx"));
		dto.setEndIdx(res.getLong("endIdx"));
		dto.setUpdateTime(res.getString("updateTime"));
		dto.setUpdaterName(res.getString("updaterName"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO docEntity (" +
                "docId, pubdate, entityType, entityId, " +
                "mention, entityTitle, field, startIdx, " +
                "endIdx, updateTime, updaterName" +
                ")VALUES(" +
                "?, ?, ?, ?, " +
                "?, ?, ?, ?, " +
                "?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		DocEntity dto = (DocEntity) baseDTO;

		paramList.add(dto.getDocId());
		paramList.add(dto.getPubdate());
		paramList.add(String.valueOf(dto.getEntityType()));
    	paramList.add(dto.getEntityId());

		paramList.add(dto.getMention());
		paramList.add(dto.getEntityTitle());
		paramList.add(String.valueOf(dto.getField()));
		paramList.add(dto.getStartIdx());

		paramList.add(dto.getEndIdx());
		paramList.add(dto.getUpdateTime());
		paramList.add(dto.getUpdaterName());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE docEntity SET pubdate = ?, entityType = ?, entityId = ?, mention = ?, entityTitle = ?, field = ?, startIdx = ?, endIdx = ?, countryId = ?, updateTime = ?, updaterName = ? WHERE docId = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		DocEntity dto = (DocEntity) baseDTO;

		paramList.add(dto.getPubdate());
		paramList.add(String.valueOf(dto.getEntityType()));
paramList.add(dto.getEntityType());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getMention());
		paramList.add(dto.getEntityTitle());
		paramList.add(String.valueOf(dto.getField()));
paramList.add(dto.getField());
		paramList.add(dto.getStartIdx());
		paramList.add(dto.getEndIdx());
		paramList.add(dto.getCountryId());
		paramList.add(dto.getUpdateTime());
		paramList.add(dto.getUpdaterName());


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

    protected int deleteByDocId(int docId) throws SQLException {
        return executeUpdate("DELETE FROM docEntity WHERE docId = ?", docId);
    }

    public List<DocEntity> getListByDocId(int docId) {
        return getListByCondition("docId = ?", docId);
    }
}
