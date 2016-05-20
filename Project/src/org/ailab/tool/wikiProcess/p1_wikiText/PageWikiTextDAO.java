package org.ailab.tool.wikiProcess.p1_wikiText;

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
 * 功能描述：指称业务逻辑
 */
public class PageWikiTextDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(PageWikiTextDAO.class);
    public boolean loadPlainText = false;


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "wiki.pageWikiText";
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
		return "SELECT count(*) FROM pageWikiText";
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
        PageWikiText dto = new PageWikiText();

		dto.setId(res.getInt("id"));
		dto.setTitle(getStringFromVarbinary(res, "title"));
		dto.setWikiText(getStringFromVarbinary(res, "wikiText"));
        if(loadPlainText) {
            dto.setPlainText(getStringFromVarbinary(res, "plainText"));
        }
//		dto.setAbst(getStringFromVarbinary(res, "abst"));

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
        PageWikiText dto = (PageWikiText) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getTitle());
		paramList.add(dto.getWikiText());
		paramList.add(dto.getPlainText());
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
        PageWikiText dto = (PageWikiText) baseDTO;

        paramList.add(dto.getTitle());
        paramList.add(dto.getWikiText());
        paramList.add(dto.getPlainText());
        paramList.add(dto.getAbst());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM pageWikiText WHERE id = ?";
	}


	/**
	 * 由id得到pageWikiText
	 * @param id id
	 */
	public PageWikiText get(int id) throws SQLException {
        PageWikiText pageWikiText;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        pageWikiText = (PageWikiText) getBySql("select * from pageWikiText where id = ?", paramList);

        return pageWikiText;
	}
}
