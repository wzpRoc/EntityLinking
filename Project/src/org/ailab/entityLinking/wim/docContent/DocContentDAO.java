package org.ailab.entityLinking.wim.docContent;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体名称业务逻辑
 */
public class DocContentDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(DocContentDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "docContent";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from docContent where docId = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM docContent";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM docContent";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM docContent WHERE docId in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		DocContent dto = new DocContent();

		dto.setDocId(res.getInt("docId"));
		dto.setContent(res.getString("content"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO docContent (docId, content)VALUES(?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		DocContent dto = (DocContent) baseDTO;

		paramList.add(dto.getDocId());
		paramList.add(dto.getContent());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE docContent SET content = ? WHERE docId = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		DocContent dto = (DocContent) baseDTO;

		paramList.add(dto.getContent());

		paramList.add(dto.getDocId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM docContent WHERE docId = ?";
	}


	/**
	 * 由docId得到docContent
	 * @param id docId
	 */
	public DocContent get(int id) throws SQLException {
        DocContent docContent;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        docContent = (DocContent) getBySql("select * from docContent where docId = ?", paramList);

        return docContent;
	}

}
