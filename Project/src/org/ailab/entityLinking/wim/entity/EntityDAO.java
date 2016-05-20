package org.ailab.entityLinking.wim.entity;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-29
 * 功能描述：实体业务逻辑
 */
public class EntityDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(EntityDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "entity";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from entity where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM entity";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM entity";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM entity WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Entity dto = new Entity();

		dto.setId(res.getInt("id"));
		dto.setWikiPageId(res.getInt("wikiPageId"));
		dto.setWikiTitle(getStringFromVarbinary(res, "wikiTitle"));
		dto.setAbst(getStringFromVarbinary(res, "abst"));
		dto.setOutlinkCount(res.getInt("outlinkCount"));
		dto.setInlinkCount(res.getInt("inlinkCount"));
		dto.setPopularity(res.getFloat("popularity"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO entity (id, wikiPageId, wikiTitle, abst, outlinkCount, inlinkCount, popularity)VALUES(?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Entity dto = (Entity) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getWikiPageId());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getAbst());
		paramList.add(dto.getOutlinkCount());
		paramList.add(dto.getInlinkCount());
		paramList.add(dto.getPopularity());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE entity SET wikiPageId = ?, wikiTitle = ?, abst = ?, outlinkCount = ?, inlinkCount = ?, popularity = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Entity dto = (Entity) baseDTO;

		paramList.add(dto.getWikiPageId());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getAbst());
		paramList.add(dto.getOutlinkCount());
		paramList.add(dto.getInlinkCount());
		paramList.add(dto.getPopularity());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM entity WHERE id = ?";
	}


	/**
	 * 由id得到entity
	 * @param id id
	 */
	public Entity get(int id) {
        Entity entity;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        entity = (Entity) getBySql("select * from entity where id = ?", paramList);

        return entity;
	}


    /**
     * 获得ID集合，可用于过滤
     */
    public Set<Integer> getIdSet() {
        return getIntSet("select id from entity");
    }
}
