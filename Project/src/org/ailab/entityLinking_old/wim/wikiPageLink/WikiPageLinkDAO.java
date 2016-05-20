package org.ailab.entityLinking_old.wim.wikiPageLink;

import org.ailab.entityLinking_old.wim.wikiRedirect.WikiRedirect;
import org.ailab.tem.DBConfig;
import org.ailab.wimfra.core.BaseDAO;
import org.ailab.wimfra.core.BaseDTO;
import org.apache.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiPageLinkDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(WikiPageLinkDAO.class);

    public WikiPageLinkDAO() {
        this.dbConnectConfig = DBConfig.wikiDB.getName();
    }


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "pageLinks";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from wikiRedirect where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM wikiRedirect";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM wikiRedirect";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM wikiRedirect WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		WikiRedirect dto = new WikiRedirect();

		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO wikiRedirect (id, entityId, predicateId, objectId, entityTitle, predicateName, objectName)VALUES(?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		WikiRedirect dto = (WikiRedirect) baseDTO;

		paramList.add(dto.getId());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE wikiRedirect SET entityId = ?, predicateId = ?, objectId = ?, entityTitle = ?, predicateName = ?, objectName = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		WikiRedirect dto = (WikiRedirect) baseDTO;

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM wikiRedirect WHERE id = ?";
	}


	/**
	 * 由id得到wikiRedirect
	 * @param id id
	 */
	public WikiRedirect get(int id) throws SQLException {
        WikiRedirect wikiRedirect;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        wikiRedirect = (WikiRedirect) getBySql("select * from wikiRedirect where id = ?", paramList);

        return wikiRedirect;
	}

    public Map<String, Integer> getNameToEntityIdMap() {
        return getStringToIntMap(
                "SELECT r.rd_from_title, e.id \n" +
                        "FROM redirect r, elkb.entity e\n" +
                        "WHERE r.rd_id=e.page_id"
        );
    }

    public HashMap<String, String> getNameToEntityTitleMap() {
        return getMap(
                "SELECT r.rd_from_title, r.rd_title \n" +
                        "FROM redirect r\n" +
                        "WHERE r.rd_namespace=0"
        );
    }

    public Set<String> getInlinksByPageTitle(String pageTitle){
        return getStringSet("SELECT p.page_title\n" +
                "FROM pagelinks pl, page p\n" +
                "WHERE p.page_id=pl.pl_from\n" +
                "AND   p.page_namespace = 0\n" +
                "AND   pl.pl_namespace=0\n" +
                "AND   pl.pl_title=?", pageTitle);
    }

}
