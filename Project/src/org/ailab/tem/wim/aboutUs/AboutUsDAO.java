package org.ailab.tem.wim.aboutUs;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者:  OneChen
 * 创建日期: 2014-05-04
 * 功能描述：关于我们业务逻辑
 */
public class AboutUsDAO extends BaseDAO {

	/** 日志工具 */
	private Logger logger = Logger.getLogger(AboutUsDAO.class);

	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "aboutUs";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from aboutUs where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM aboutUs";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM aboutUs";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM aboutUs WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		AboutUs dto = new AboutUs();

		dto.setId(res.getInt("id"));
		dto.setCategoryId(res.getInt("categoryId"));
		dto.setCategoryName(res.getString("categoryName"));
		dto.setContent(res.getString("content"));
		dto.setDescription(res.getString("description"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO aboutUs (id, categoryId, categoryName, content, description)VALUES(?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		AboutUs dto = (AboutUs) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getCategoryId());
		paramList.add(dto.getCategoryName());
		paramList.add(dto.getContent());
		paramList.add(dto.getDescription());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE aboutUs SET categoryId = ?, categoryName = ?, content = ?, description = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		AboutUs dto = (AboutUs) baseDTO;

		paramList.add(dto.getCategoryId());
		paramList.add(dto.getCategoryName());
		paramList.add(dto.getContent());
		paramList.add(dto.getDescription());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM aboutUs WHERE id = ?";
	}


	/**
	 * 由id得到aboutUs
	 * @param id id
	 */
	public AboutUs get(int id) throws SQLException {
        AboutUs aboutUs;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        aboutUs = (AboutUs) getBySql("select * from aboutUs where id = ?", paramList);

        return aboutUs;
	}

}
