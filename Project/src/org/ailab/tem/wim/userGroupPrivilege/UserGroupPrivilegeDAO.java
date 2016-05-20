package org.ailab.tem.wim.userGroupPrivilege;

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
 * 功能描述：用户组权限表业务逻辑
 */
public class UserGroupPrivilegeDAO extends BaseDAO {
    public UserGroupPrivilegeDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }


	/**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "userGroupPrivilege";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from userGroupPrivilege where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM userGroupPrivilege";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM userGroupPrivilege";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM userGroupPrivilege WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		UserGroupPrivilege dto = new UserGroupPrivilege();

		dto.setId(res.getInt("id"));
		dto.setUserGroupId(res.getInt("userGroupId"));
		dto.setPrivilegeId(res.getInt("privilegeId"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO userGroupPrivilege (id, userGroupId, privilegeId)VALUES(?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		UserGroupPrivilege dto = (UserGroupPrivilege) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getUserGroupId());
		paramList.add(dto.getPrivilegeId());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE userGroupPrivilege SET userGroupId = ?, privilegeId = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		UserGroupPrivilege dto = (UserGroupPrivilege) baseDTO;

		paramList.add(dto.getUserGroupId());
		paramList.add(dto.getPrivilegeId());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM userGroupPrivilege WHERE id = ?";
	}


	/**
	 * 由id得到userGroupPrivilege
	 * @param id id
	 */
	public UserGroupPrivilege get(int id) throws SQLException {
        UserGroupPrivilege userGroupPrivilege;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        userGroupPrivilege = (UserGroupPrivilege) getBySql("select * from userGroupPrivilege where id = ?", paramList);

        return userGroupPrivilege;
	}

    /**
     * 根据用户组ID删除用户组所有权限
     * @param groupId
     * @throws java.sql.SQLException
     */
    public void deleteByGroupId(int groupId) throws SQLException {
        deleteByCondition("userGroupId = ?", groupId);
    }

}
