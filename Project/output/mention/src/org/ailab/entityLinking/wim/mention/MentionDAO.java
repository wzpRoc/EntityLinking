package org.ailab.entityLinking.wim.mention;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：指称业务逻辑
 */
public class MentionDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(MentionDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "mention";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from mention where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM mention";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM mention";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM mention WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Mention dto = new Mention();

		dto.setId(res.getInt("id"));
		dto.setDocId(res.getInt("docId"));
		dto.setSeq(res.getInt("seq"));
		dto.setStartIdx(res.getInt("startIdx"));
		dto.setEndIdx(res.getInt("endIdx"));
		dto.setName(res.getString("name"));
		dto.setEntityId(res.getInt("entityId"));
		dto.setWikiTitle(res.getString("wikiTitle"));
		dto.setInitialWeightInDoc(res.getFloat("initialWeightInDoc"));
		dto.setTf(res.getDouble("tf"));
		dto.setIdf(res.getDouble("idf"));
		dto.setTfidf(res.getDouble("tfidf"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO mention (id, docId, seq, startIdx, endIdx, name, entityId, wikiTitle, initialWeightInDoc, tf, idf, tfidf)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Mention dto = (Mention) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getDocId());
		paramList.add(dto.getSeq());
		paramList.add(dto.getStartIdx());
		paramList.add(dto.getEndIdx());
		paramList.add(dto.getName());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getInitialWeightInDoc());
		paramList.add(dto.getTf());
		paramList.add(dto.getIdf());
		paramList.add(dto.getTfidf());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE mention SET docId = ?, seq = ?, startIdx = ?, endIdx = ?, name = ?, entityId = ?, wikiTitle = ?, initialWeightInDoc = ?, tf = ?, idf = ?, tfidf = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Mention dto = (Mention) baseDTO;

		paramList.add(dto.getDocId());
		paramList.add(dto.getSeq());
		paramList.add(dto.getStartIdx());
		paramList.add(dto.getEndIdx());
		paramList.add(dto.getName());
		paramList.add(dto.getEntityId());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getInitialWeightInDoc());
		paramList.add(dto.getTf());
		paramList.add(dto.getIdf());
		paramList.add(dto.getTfidf());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM mention WHERE id = ?";
	}


	/**
	 * 由id得到mention
	 * @param id id
	 */
	public Mention get(int id) throws SQLException {
        Mention mention;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        mention = (Mention) getBySql("select * from mention where id = ?", paramList);

        return mention;
	}

}
