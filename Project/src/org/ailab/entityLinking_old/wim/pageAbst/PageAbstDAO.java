package org.ailab.entityLinking_old.wim.pageAbst;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-15
 * 功能描述：维基页面摘要业务逻辑
 */
public class PageAbstDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(PageAbstDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "pageAbst";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from pageAbst where page_id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM pageAbst";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM pageAbst";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM pageAbst WHERE page_id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		PageAbst dto = new PageAbst();

		dto.setPage_id(res.getInt("page_id"));
		dto.setPage_title(res.getString("page_title"));
		dto.setPage_abst(res.getString("page_abst"));
		dto.setPage_abst_len(res.getInt("page_abst_len"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO pageAbst (page_id, page_title, page_abst, page_abst_len)VALUES(?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		PageAbst dto = (PageAbst) baseDTO;

		paramList.add(dto.getPage_id());
		paramList.add(dto.getPage_title());
		paramList.add(dto.getPage_abst());
		paramList.add(dto.getPage_abst_len());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE pageAbst SET page_title = ?, page_abst = ?, page_abst_len = ? WHERE page_id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		PageAbst dto = (PageAbst) baseDTO;

		paramList.add(dto.getPage_title());
		paramList.add(dto.getPage_abst());
		paramList.add(dto.getPage_abst_len());

		paramList.add(dto.getPage_id());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM pageAbst WHERE page_id = ?";
	}


	/**
	 * 由page_id得到pageAbst
	 * @param id page_id
	 */
	public PageAbst get(int id) throws SQLException {
        PageAbst pageAbst;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        pageAbst = (PageAbst) getBySql("select * from pageAbst where page_id = ?", paramList);

        return pageAbst;
	}

}
