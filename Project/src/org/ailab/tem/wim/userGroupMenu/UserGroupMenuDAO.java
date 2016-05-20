package org.ailab.tem.wim.userGroupMenu;

import org.ailab.tem.DBConfig;
import org.ailab.tem.wim.menu.Menu;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：用户组菜单业务逻辑
 */
public class UserGroupMenuDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(UserGroupMenuDAO.class);

    public UserGroupMenuDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }


    /**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "userGroupMenu";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from userGroupMenu where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM userGroupMenu";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM userGroupMenu";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM userGroupMenu WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		UserGroupMenu dto = new UserGroupMenu();

		dto.setId(res.getInt("id"));
		dto.setUserGroupId(res.getInt("userGroupId"));
		dto.setMenuId(res.getInt("menuId"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO userGroupMenu (id, userGroupId, menuId)VALUES(?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		UserGroupMenu dto = (UserGroupMenu) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getUserGroupId());
		paramList.add(dto.getMenuId());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE userGroupMenu SET userGroupId = ?, menuId = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		UserGroupMenu dto = (UserGroupMenu) baseDTO;

		paramList.add(dto.getUserGroupId());
		paramList.add(dto.getMenuId());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM userGroupMenu WHERE id = ?";
	}


    @Override
    public List getListByIds(String ids) throws Exception {
        return super.getListByIds(ids);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
	 * 由id得到userGroupMenu
	 * @param id id
	 */
	public UserGroupMenu get(int id) throws SQLException {
        UserGroupMenu userGroupMenu;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        userGroupMenu = (UserGroupMenu) getBySql("SELECT * FROM userGroupMenu WHERE id = ?", paramList);

        return userGroupMenu;
	}

    public Set<Integer> getMenuIdSetByGroupId(int groupId) throws SQLException {
        return getIntSet("SELECT menuId FROM userGroupMenu WHERE userGroupId = ?", groupId);
    }

    public int deleteByGroupId(int groupId) throws SQLException {
        return deleteByCondition("userGroupId = ? ", groupId);
    }

}
