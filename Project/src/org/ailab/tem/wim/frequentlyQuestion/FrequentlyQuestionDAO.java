package org.ailab.tem.wim.frequentlyQuestion;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-22
 * 功能描述：常见问题业务逻辑
 */
public class FrequentlyQuestionDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(FrequentlyQuestionDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "frequentlyQuestion";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from frequentlyQuestion where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM frequentlyQuestion";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM frequentlyQuestion";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM frequentlyQuestion WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		FrequentlyQuestion dto = new FrequentlyQuestion();

		dto.setId(res.getInt("id"));
		dto.setCatalogId(res.getInt("catalogId"));
		dto.setQuestion(res.getString("question"));
		dto.setAnswer(res.getString("answer"));
		dto.setEditorName(res.getString("editorName"));
		dto.setEditTime(res.getString("editTime"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO frequentlyQuestion (id, catalogId, question, answer, editorName, editTime)VALUES(?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		FrequentlyQuestion dto = (FrequentlyQuestion) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getCatalogId());
		paramList.add(dto.getQuestion());
		paramList.add(dto.getAnswer());
		paramList.add(dto.getEditorName());
		paramList.add(dto.getEditTime());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE frequentlyQuestion SET catalogId = ?, question = ?, answer = ?, editorName = ?, editTime = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		FrequentlyQuestion dto = (FrequentlyQuestion) baseDTO;

		paramList.add(dto.getCatalogId());
		paramList.add(dto.getQuestion());
		paramList.add(dto.getAnswer());
		paramList.add(dto.getEditorName());
		paramList.add(dto.getEditTime());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM frequentlyQuestion WHERE id = ?";
	}


	/**
	 * 由id得到frequentlyQuestion
	 * @param id id
	 */
	public FrequentlyQuestion get(int id) throws SQLException {
        FrequentlyQuestion frequentlyQuestion;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        frequentlyQuestion = (FrequentlyQuestion) getBySql("select * from frequentlyQuestion where id = ?", paramList);

        return frequentlyQuestion;
	}


    public int deleteByCategoryId(int catalogId) throws SQLException {
        return deleteByCondition("catalogId=?", catalogId);
    }


}
