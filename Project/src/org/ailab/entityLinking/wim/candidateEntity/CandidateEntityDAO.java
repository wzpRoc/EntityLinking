package org.ailab.entityLinking.wim.candidateEntity;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：指称的候选实体业务逻辑
 */
public class CandidateEntityDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(CandidateEntityDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "candidateEntity";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from candidateEntity where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM candidateEntity";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM candidateEntity";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM candidateEntity WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		CandidateEntity dto = new CandidateEntity();

		dto.setId(res.getInt("id"));
		dto.setDocId(res.getInt("docId"));
		dto.setSeqInDoc(res.getInt("seqInDoc"));
		dto.setMentionId(res.getInt("mentionId"));
		dto.setMentionName(getStringFromVarbinary(res, "mentionName"));
		dto.setEntityId(res.getInt("entityId"));
		dto.setWikiTitle(getStringFromVarbinary(res, "wikiTitle"));
		dto.setProbOfMentionToEntity(res.getDouble("probOfMentionToEntity"));
		dto.setProbOfNameToEntity(res.getDouble("probOfNameToEntity"));
		dto.setRankByProbOfNameToEntity(res.getInt("rankByProbOfNameToEntity"));
		dto.setRankByProbOfMentionToEntity(res.getInt("rankByProbOfMentionToEntity"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO candidateEntity (id, docId, seqInDoc, mentionId, mentionName, entityId, wikiTitle, probOfMentionToEntity, probOfNameToEntity, rankByProbOfNameToEntity, rankByProbOfMentionToEntity)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		CandidateEntity dto = (CandidateEntity) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getDocId());
		paramList.add(dto.getSeqInDoc());
		paramList.add(dto.getMentionId());
		paramList.add(dto.getMentionName());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getProbOfMentionToEntity());
		paramList.add(dto.getProbOfNameToEntity());
		paramList.add(dto.getRankByProbOfNameToEntity());
		paramList.add(dto.getRankByProbOfMentionToEntity());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE candidateEntity SET docId = ?, seqInDoc = ?, mentionId = ?, mentionName = ?, entityId = ?, wikiTitle = ?, probOfMentionToEntity = ?, probOfNameToEntity = ?, rankByProbOfNameToEntity = ?, rankByProbOfMentionToEntity = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		CandidateEntity dto = (CandidateEntity) baseDTO;

		paramList.add(dto.getDocId());
		paramList.add(dto.getSeqInDoc());
		paramList.add(dto.getMentionId());
		paramList.add(dto.getMentionName());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getProbOfMentionToEntity());
		paramList.add(dto.getProbOfNameToEntity());
		paramList.add(dto.getRankByProbOfNameToEntity());
		paramList.add(dto.getRankByProbOfMentionToEntity());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM candidateEntity WHERE id = ?";
	}


	/**
	 * 由id得到candidateEntity
	 * @param id id
	 */
	public CandidateEntity get(int id) throws SQLException {
        CandidateEntity candidateEntity;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        candidateEntity = (CandidateEntity) getBySql("select * from candidateEntity where id = ?", paramList);

        return candidateEntity;
	}


    /**
     * 根据mentionId获得候选实体
     */
    public List<CandidateEntity> getListByMentionId(int mentionId, int maxCandidates) {
        // todo: probOfMentionToEntity
        String condition = "mentionId=? and probOfMentionToEntity<"+maxCandidates+" ORDER BY probOfMentionToEntity";
        return getListByCondition(condition, mentionId);
    }

}
