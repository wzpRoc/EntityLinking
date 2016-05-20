package org.ailab.entityLinking.wim.doc;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-18
 * 功能描述：文档业务逻辑
 */
public class DocDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(DocDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "doc";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from doc where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM doc";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM doc";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM doc WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Doc dto = new Doc();

		dto.setId(res.getInt("id"));
		dto.setTitle(getStringFromVarbinary(res, "title"));
		dto.setContent(res.getString("content"));
		dto.setTokens(res.getString("tokens"));
		dto.setUrl(getStringFromVarbinary(res, "url"));
		dto.setToeTag(getStringFromVarbinary(res, "toeTag"));
		dto.setPubDate(res.getString("pubDate"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO doc (id, title, content, tokens, url, toeTag, pubDate)VALUES(?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Doc dto = (Doc) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getTitle());
		paramList.add(dto.getContent());
		paramList.add(dto.getTokens());
		paramList.add(dto.getUrl());
		paramList.add(dto.getToeTag());
		paramList.add(dto.getPubDate());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE doc SET title = ?, content = ?, tokens = ?, url = ?, toeTag = ?, pubDate = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Doc dto = (Doc) baseDTO;

		paramList.add(dto.getTitle());
		paramList.add(dto.getContent());
		paramList.add(dto.getTokens());
		paramList.add(dto.getUrl());
		paramList.add(dto.getToeTag());
		paramList.add(dto.getPubDate());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM doc WHERE id = ?";
	}


	/**
	 * 由id得到doc
	 * @param id id
	 */
	public Doc get(int id) throws SQLException {
        Doc doc;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        doc = (Doc) getBySql("select * from doc where id = ?", paramList);

        return doc;
	}

}
