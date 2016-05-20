package org.ailab.tem.wim.privilege;

import org.ailab.tem.DBConfig;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-06-21
 * 功能描述：权限表业务逻辑
 */
public class PrivilegeDAO extends BaseDAO {
    public PrivilegeDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "privilege";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from privilege where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM privilege";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM privilege";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM privilege WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Privilege dto = new Privilege();

		dto.setId(res.getInt("id"));
		dto.setDescription(res.getString("description"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO privilege (id, description)VALUES(?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Privilege dto = (Privilege) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getDescription());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE privilege SET description = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Privilege dto = (Privilege) baseDTO;

		paramList.add(dto.getDescription());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM privilege WHERE id = ?";
	}


	/**
	 * 由id得到privilege
	 * @param id id
	 */
	public Privilege get(int id) throws SQLException {
        Privilege privilege;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        privilege = (Privilege) getBySql("select * from privilege where id = ?", paramList);

        return privilege;
	}

}
