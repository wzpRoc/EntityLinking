package org.ailab.entityLinking.wim.entityToEntityLink;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-21
 * 功能描述：实体到实体的链接业务逻辑
 */
public class EntityToEntityLinkDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(EntityToEntityLinkDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "entityToEntityLink";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from entityToEntityLink where fromEntityId = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM " + tableName;
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM entityToEntityLink";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM entityToEntityLink WHERE fromEntityId in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		EntityToEntityLink dto = new EntityToEntityLink();

		dto.setFromEntityId(res.getInt("fromEntityId"));
		dto.setToEntityId(res.getInt("toEntityId"));
//		dto.setToWikiTitle(res.getString("toWikiTitle"));
//		dto.setCnt(res.getInt("cnt"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
//		return "INSERT INTO entityToEntityLink (fromEntityId, toEntityId, toWikiTitle, cnt)VALUES(?, ?, ?, ?)";
		return "INSERT INTO entityToEntityLink (fromEntityId, toEntityId)VALUES(?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		EntityToEntityLink dto = (EntityToEntityLink) baseDTO;

		paramList.add(dto.getFromEntityId());
		paramList.add(dto.getToEntityId());
//		paramList.add(dto.getToWikiTitle());
//		paramList.add(dto.getCnt());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE entityToEntityLink SET toEntityId = ?, toWikiTitle = ?, cnt = ? WHERE fromEntityId = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		EntityToEntityLink dto = (EntityToEntityLink) baseDTO;

		paramList.add(dto.getToEntityId());
//		paramList.add(dto.getToWikiTitle());
//		paramList.add(dto.getCnt());

//		paramList.add(dto.getnull());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM entityToEntityLink WHERE fromEntityId = ?";
	}


	/**
	 * 由fromEntityId得到entityToEntityLink
	 * @param id fromEntityId
	 */
	public EntityToEntityLink get(int id) throws SQLException {
        EntityToEntityLink entityToEntityLink;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        entityToEntityLink = (EntityToEntityLink) getBySql("select * from entityToEntityLink where fromEntityId = ?", paramList);

        return entityToEntityLink;
	}


    /**
     * 获得两个实体的入链交集大小
     */
    public int getIntersectionCount(int entityId0, int entityId1) {
        String sql =
                "SELECT COUNT(*)\n" +
                "FROM el.entityToEntityLink eel0, el.entityToEntityLink eel1\n" +
                "WHERE eel0.toEntityId=?\n" +
                "AND   eel1.toEntityId=?\n" +
                "AND   eel0.fromEntityId=eel1.fromEntityId";
        return getInt(sql, entityId0, entityId1);
    }


    /**
     * 获得一个实体的入链集合
     */
    public Set<Integer> getInlinkSet(int toEntityId) {
        String sql = "SELECT fromEntityId FROM entityToEntityLink WHERE toEntityId=?";
        return getIntSet(sql, toEntityId);
    }

}
