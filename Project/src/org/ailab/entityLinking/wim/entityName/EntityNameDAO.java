package org.ailab.entityLinking.wim.entityName;

import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-12-20
 * 功能描述：实体名称业务逻辑
 */
public class EntityNameDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(EntityNameDAO.class);


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "entityName";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from entityName where name = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM entityName";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM entityName";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM entityName WHERE name in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		EntityName dto = new EntityName();

		dto.setEntityId(res.getInt("entityId"));
		dto.setName(getStringFromVarbinary(res, "name"));
		dto.setWikiTitle(getStringFromVarbinary(res, "wikiTitle"));
		dto.setProbOfNameToEntity(res.getFloat("probOfNameToEntity"));
		dto.setProbOfEntityToName(res.getFloat("probOfEntityToName"));
        dto.setSeq(res.getInt("seq"));


        return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO " + tableName + "(entityId, name, wikiTitle, probOfNameToEntity, probOfEntityToName, seq)VALUES(?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		EntityName dto = (EntityName) baseDTO;

		paramList.add(dto.getEntityId());
		paramList.add(dto.getName());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getProbOfNameToEntity());
		paramList.add(dto.getProbOfEntityToName());
		paramList.add(dto.getSeq());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE entityName SET name = ?, wikiTitle = ?, probOfNameToEntity = ?, probOfEntityToName = ?, seq = ? WHERE name = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		EntityName dto = (EntityName) baseDTO;

		paramList.add(dto.getName());
		paramList.add(dto.getWikiTitle());
		paramList.add(dto.getProbOfNameToEntity());
		paramList.add(dto.getProbOfEntityToName());
		paramList.add(dto.getSeq());

		paramList.add(dto.getName());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM entityName WHERE name = ?";
	}


	/**
	 * 由name得到entityName
	 * @param id name
	 */
	public EntityName get(int id) throws SQLException {
        EntityName entityName;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        entityName = (EntityName) getBySql("select * from entityName where name = ?", paramList);

        return entityName;
	}


    /**
     * 根据名称查找
     * @param name
     * @return
     */
    public List<EntityName> getListByName(String name) {
        //noinspection unchecked
        return getListByCondition("name=? ORDER BY probOfNameToEntity DESC", name);
    }


    /**
     * 根据小写名称查找
     * @param lcName
     * @return
     */
    public List<EntityName> getListByLcName(String lcName) {
        //noinspection unchecked
        return getListByCondition("lcName=?", lcName);
    }


    /**
     * 根据名称查找，选择最大的概率
     * @param name
     * @return
     */
    public List<EntityName> getMaxListByName(String name) {
        //noinspection unchecked
        return getList(
                "SELECT entityId, name, MAX(wikiTitle) wikiTitle, MAX(probOfNameToEntity) probOfNameToEntity, MAX(probOfEntityToName) probOfEntityToName, seq\n" +
                        "FROM   "+tableName+"\n" +
                        "WHERE  name=?\n" +
                        "GROUP  BY entityId, name\n" +
                        "ORDER  BY probOfNameToEntity DESC\n" +
                        "LIMIT  1000",
                name);
    }


    /**
     * 根据小写名称查找，并汇总
     * @param name
     * @return
     */
    public List<EntityName> getAverageListByLcName(String name) {
        //noinspection unchecked
        return getList(
                "SELECT entityId, '"+name.replace("'", "''")+"' name, MIN(wikiTitle) wikiTitle, AVG(probOfNameToEntity) probOfNameToEntity, AVG(probOfEntityToName) probOfEntityToName, 0 seq\n" +
                        "FROM   " + tableName + "\n" +
                        "WHERE  lcName=?\n" +
                        "GROUP  BY entityId, lcName\n" +
                        "ORDER  BY probOfNameToEntity DESC\n" +
                        "LIMIT  1000",
                name.toLowerCase());
    }

}
