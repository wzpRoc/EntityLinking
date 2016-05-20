package org.ailab.wikipedia.wim.article;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-01-02
 * 功能描述：文章业务逻辑
 */
public class ArticleDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(ArticleDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "article";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from article where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM article";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM article";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM article WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Article dto = new Article();

		dto.setId(res.getInt("id"));
		dto.setTitle(res.getString("title"));
		dto.setNormalizedTitle(res.getString("normalizedTitle"));
		dto.setAbst(res.getString("abst"));
		dto.setLink(res.getString("link"));
		dto.setPhotoLink(res.getString("photoLink"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO article (id, title, normalizedTitle, abst, link, photoLink)VALUES(?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Article dto = (Article) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getTitle());
		paramList.add(dto.getNormalizedTitle());
		paramList.add(dto.getAbst());
		paramList.add(dto.getLink());
		paramList.add(dto.getPhotoLink());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE article SET title = ?, normalizedTitle = ?, abst = ?, link = ?, photoLink = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Article dto = (Article) baseDTO;

		paramList.add(dto.getTitle());
        paramList.add(dto.getNormalizedTitle());
        paramList.add(dto.getAbst());
		paramList.add(dto.getLink());
        paramList.add(dto.getPhotoLink());

        paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM article WHERE id = ?";
	}


	/**
	 * 由id得到article
	 * @param id id
	 */
	public Article get(int id) throws SQLException {
        Article article;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        article = (Article) getBySql("select * from article where id = ?", paramList);

        return article;
	}

}
