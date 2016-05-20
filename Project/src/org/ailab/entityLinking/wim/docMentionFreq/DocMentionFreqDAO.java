package org.ailab.entityLinking.wim.docMentionFreq;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：实体名称业务逻辑
 */
public class DocMentionFreqDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(DocMentionFreqDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "docMentionFreq";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from docMentionFreq where mentionName = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM docMentionFreq";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM docMentionFreq";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM docMentionFreq WHERE mentionName in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		DocMentionFreq dto = new DocMentionFreq();

		dto.setMentionName(getStringFromVarbinary(res, "mentionName"));
		dto.setFreq(res.getInt("freq"));
		dto.setIdf(res.getDouble("idf"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO docMentionFreq (mentionName, freq, idf)VALUES(?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		DocMentionFreq dto = (DocMentionFreq) baseDTO;

		paramList.add(dto.getMentionName());
		paramList.add(dto.getFreq());
		paramList.add(dto.getIdf());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE docMentionFreq SET freq = ?, idf = ? WHERE mentionName = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		DocMentionFreq dto = (DocMentionFreq) baseDTO;

		paramList.add(dto.getFreq());
		paramList.add(dto.getIdf());

		paramList.add(dto.getMentionName());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM docMentionFreq WHERE mentionName = ?";
	}


	/**
	 * 由mentionName得到docMentionFreq
	 * @param id mentionName
	 */
	public DocMentionFreq get(int id) throws SQLException {
        DocMentionFreq docMentionFreq;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        docMentionFreq = (DocMentionFreq) getBySql("select * from docMentionFreq where mentionName = ?", paramList);

        return docMentionFreq;
	}

}
