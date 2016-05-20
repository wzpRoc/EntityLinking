package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.wimfra.core.BaseDAO;
import org.ailab.wimfra.core.BaseDTO;
import org.ailab.wimfra.util.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-24
 * 功能描述：页面摘要数据访问对象
 */
public class PageAbstDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(PageAbstDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "wiki.pageAbst";
	}

    /**
     * 得到正在使用的表名
     */
    public String getCurrentTableName() {
        return tableName;
    }

	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from "+tableName+" where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM "+tableName+"";
	}


    protected String getDefaultOrderBy() {
        return "title";
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
		return "DELETE FROM "+tableName+" WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
        PageAbst dto = new PageAbst();

		dto.setId(res.getInt("id"));
		dto.setTitle(getStringFromVarbinary(res, "title"));
		dto.setAbst(getStringFromVarbinary(res, "abst"));

		return dto;
	}

    /**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO "+tableName+" (id, title, wikiText, plainText, abst)VALUES(?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
        PageAbst dto = (PageAbst) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getTitle());
		paramList.add(dto.getAbst());
	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE "+tableName+" SET docId = ?, seqInDoc = ?, startIdx = ?, endIdx = ?, name = ?, entityId = ?, wikiTitle = ?, initialWeightInDoc = ?, tf = ?, idf = ?, tfidf = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
        PageAbst dto = (PageAbst) baseDTO;

        paramList.add(dto.getTitle());
        paramList.add(dto.getAbst());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM pageAbst WHERE id = ?";
	}


	/**
	 * 由id得到pageAbst
	 * @param id id
	 */
	public PageAbst get(int id) {
        PageAbst pageAbst;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        pageAbst = (PageAbst) getBySql("select * from pageAbst where id = ?", paramList);

        return pageAbst;
	}


	/**
	 * 由entityId得到pageAbst
	 * @param entityId
	 */
	public PageAbst getByEntityId(int entityId) {
        PageAbst pageAbst = (PageAbst) getByCondition("entityId = ?", entityId);

        return pageAbst;
	}
}
