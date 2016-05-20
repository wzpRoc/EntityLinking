package org.ailab.entityLinking.wim.docWordFreq;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：新闻数据中词出现的频次业务逻辑
 */
public class DocWordFreqDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(DocWordFreqDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "docWordFreq";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from docWordFreq where wordName = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM docWordFreq";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM docWordFreq";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM docWordFreq WHERE wordName in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		DocWordFreq dto = new DocWordFreq();

		dto.setWordName(getStringFromVarbinary(res, "wordName"));
		dto.setFreq(res.getInt("freq"));
		dto.setIdf(res.getDouble("idf"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO docWordFreq (wordName, freq, idf)VALUES(?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		DocWordFreq dto = (DocWordFreq) baseDTO;

		paramList.add(dto.getWordName());
		paramList.add(dto.getFreq());
		paramList.add(dto.getIdf());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE docWordFreq SET freq = ?, idf = ? WHERE wordName = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		DocWordFreq dto = (DocWordFreq) baseDTO;

		paramList.add(dto.getFreq());
		paramList.add(dto.getIdf());

		paramList.add(dto.getWordName());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM docWordFreq WHERE wordName = ?";
	}


	/**
	 * 由wordName得到docWordFreq
	 * @param id wordName
	 */
	public DocWordFreq get(int id) throws SQLException {
        DocWordFreq docWordFreq;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        docWordFreq = (DocWordFreq) getBySql("select * from docWordFreq where wordName = ?", paramList);

        return docWordFreq;
	}

}
