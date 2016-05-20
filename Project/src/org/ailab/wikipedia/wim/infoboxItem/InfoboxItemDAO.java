package org.ailab.wikipedia.wim.infoboxItem;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：信息框条目业务逻辑
 */
public class InfoboxItemDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(InfoboxItemDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "infoboxItem";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from infoboxItem where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM infoboxItem";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM infoboxItem";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM infoboxItem WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		InfoboxItem dto = new InfoboxItem();

		dto.setId(res.getInt("id"));
		dto.setArticleId(res.getInt("articleId"));
		dto.setTitle(res.getString("title"));
		dto.setValue(res.getString("value"));
		dto.setNormalizedValue(res.getString("normalizedValue"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO infoboxItem (id, articleId, title, value, normalizedValue)VALUES(?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		InfoboxItem dto = (InfoboxItem) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getArticleId());
		paramList.add(dto.getTitle());
		paramList.add(StringUtil.subString(dto.getValue(), 0, 1023));
		paramList.add(StringUtil.subString(dto.getNormalizedValue(), 0, 1023));
	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE infoboxItem SET articleId = ?, title = ?, value = ?, normalizedValue = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		InfoboxItem dto = (InfoboxItem) baseDTO;

		paramList.add(dto.getArticleId());
		paramList.add(dto.getTitle());
		paramList.add(dto.getValue());
        paramList.add(dto.getNormalizedValue());

        paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM infoboxItem WHERE id = ?";
	}


	/**
	 * 由id得到infoboxItem
	 * @param id id
	 */
	public InfoboxItem get(int id) throws SQLException {
        InfoboxItem infoboxItem;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        infoboxItem = (InfoboxItem) getBySql("select * from infoboxItem where id = ?", paramList);

        return infoboxItem;
	}

}
