package org.ailab.entityLinking.wim.doc_lob;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档大对象业务逻辑
 */
public class Doc_lobDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(Doc_lobDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "doc_lob";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from doc_lob where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM doc_lob";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM doc_lob";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM doc_lob WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Doc_lob dto = new Doc_lob();

		dto.setId(res.getInt("id"));
		dto.setAbst(res.getString("abst"));
		dto.setContent(res.getString("content"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO doc_lob (id, abst, content)VALUES(?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Doc_lob dto = (Doc_lob) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getAbst());
		paramList.add(dto.getContent());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE doc_lob SET abst = ?, content = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Doc_lob dto = (Doc_lob) baseDTO;

		paramList.add(dto.getAbst());
		paramList.add(dto.getContent());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM doc_lob WHERE id = ?";
	}


	/**
	 * 由id得到doc_lob
	 * @param id id
	 */
	public Doc_lob get(int id) throws SQLException {
        Doc_lob doc_lob;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        doc_lob = (Doc_lob) getBySql("select * from doc_lob where id = ?", paramList);

        return doc_lob;
	}

}
