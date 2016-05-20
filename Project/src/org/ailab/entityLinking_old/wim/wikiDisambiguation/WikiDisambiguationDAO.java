package org.ailab.entityLinking_old.wim.wikiDisambiguation;

import org.ailab.tem.DBConfig;
import org.ailab.wimfra.core.BaseDAO;
import org.ailab.wimfra.core.BaseDTO;
import org.apache.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-06
 * 功能描述：维基百科事实业务逻辑
 */
public class WikiDisambiguationDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(WikiDisambiguationDAO.class);

    public WikiDisambiguationDAO() {
        this.dbConnectConfig = DBConfig.wikiDB.getName();
    }

    /**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "wikiDisambiguation";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from wikiDisambiguation where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM wikiDisambiguation";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM wikiDisambiguation";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM wikiDisambiguation WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		WikiDisambiguation dto = new WikiDisambiguation();
        dto.setFromTitle(res.getString("pl_from_title_normalized"));
        dto.setToTitle(res.getString("pl_title"));

		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO wikiDisambiguation (id, entityId, predicateId, objectId, entityTitle, predicateName, objectName)VALUES(?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		WikiDisambiguation dto = (WikiDisambiguation) baseDTO;

		paramList.add(dto.getId());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE wikiDisambiguation SET entityId = ?, predicateId = ?, objectId = ?, entityTitle = ?, predicateName = ?, objectName = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		WikiDisambiguation dto = (WikiDisambiguation) baseDTO;

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM wikiDisambiguation WHERE id = ?";
	}


	/**
	 * 由id得到wikiDisambiguation
	 * @param id id
	 */
	public WikiDisambiguation get(int id) throws SQLException {
        WikiDisambiguation wikiDisambiguation;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        wikiDisambiguation = (WikiDisambiguation) getBySql("select * from wikiDisambiguation where id = ?", paramList);

        return wikiDisambiguation;
	}

    public Map<String, Integer> getNameToEntityIdMap() {
        return getStringToIntMap(
                "SELECT r.rd_from_title, r.rd_id \n" +
                        "FROM wzp.wiki r, elkb.entity e\n" +
                        "WHERE r.rd_id=e.id\n" +
                        "AND e.type<>' '"
        );
    }

    public int getPageLinkCount(String title) {
        return getInt(
                "SELECT COUNT(*) \n" +
                        "FROM wzp.wiki.pagelinks\n" +
                        "WHERE pl_namespace=0\n" +
                        "AND pl_title=?", title);
    }

}
