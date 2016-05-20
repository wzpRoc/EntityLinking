package org.ailab.tem.wim.userGroupUser;

import org.ailab.tem.DBConfig;
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
 * 功能描述：用户组成员业务逻辑
 */
public class UserGroupUserDAO extends BaseDAO {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(UserGroupUserDAO.class);

    public UserGroupUserDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }


    /**
     * 得到表名
     *
     * @return 表名
     */
    protected String getTableName() {
        return "userGroupUser";
    }


    /**
     * 得到查询一条记录的SQL
     *
     * @return 查询一条记录的SQL
     */
    protected String getSqlForGet() {
        return "select * from userGroupUser where id = ?";
    }


    /**
     * 得到查询多条记录的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForGetList() {
        return "SELECT * FROM userGroupUser";
    }


    /**
     * 得到查询记录总数的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForCount() {
        return "SELECT count(*) FROM userGroupUser";
    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete(String keys) {
        return "DELETE FROM userGroupUser WHERE id in (" + keys + ")";
    }


    /**
     * 将resultSet中的值设置到dto中
     *
     * @param res
     * @return dto
     * @throws java.sql.SQLException
     */
    protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
        UserGroupUser dto = new UserGroupUser();

        dto.setId(res.getInt("id"));
        dto.setUserGroupId(res.getInt("userGroupId"));
        dto.setUserId(res.getInt("userId"));
        dto.setUserState(res.getInt("userState"));


        return dto;
    }


    /**
     * 得到insert语句的SQL
     *
     * @return insert语句的SQL
     */
    protected String getSqlForInsert() {
        return "INSERT INTO userGroupUser (id, userGroupId, userId, userState)VALUES(?, ?, ?, ?)";
    }


    /**
     * 为insert设置参数
     */
    protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
        UserGroupUser dto = (UserGroupUser) baseDTO;

        paramList.add(dto.getId());
        paramList.add(dto.getUserGroupId());
        paramList.add(dto.getUserId());
        paramList.add(dto.getUserState());

    }


    /**
     * 得到update语句的SQL
     *
     * @return update语句的SQL
     */
    protected String getSqlForUpdate() {
        return "UPDATE userGroupUser SET userGroupId = ?, userId = ?, userState = ? WHERE id = ?";
    }


    /**
     * 为update设置参数
     */
    protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
        UserGroupUser dto = (UserGroupUser) baseDTO;

        paramList.add(dto.getUserGroupId());
        paramList.add(dto.getUserId());
        paramList.add(dto.getUserState());

        paramList.add(dto.getId());

    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete() {
        return "DELETE FROM userGroupUser WHERE id = ?";
    }


    /**
     * 由id得到userGroupUser
     *
     * @param id id
     */
    public UserGroupUser get(int id) throws SQLException {
        UserGroupUser userGroupUser;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        userGroupUser = (UserGroupUser) getBySql("select * from userGroupUser where id = ?", paramList);

        return userGroupUser;
    }


    public Set<Integer> getGroupIdSetByUserId(int userId) throws SQLException {
        return getIntSet("SELECT userGroupId FROM " + getTableName() + " WHERE userId=?", userId);
    }

    public Set<Integer> getUserIdSetByGroupId(int groupId) throws SQLException {
        return getIntSet("SELECT userId FROM userGroupUser WHERE userGroupId = ?", groupId);
    }


    public int deleteByGroupId(int groupId) throws SQLException {
        return deleteByCondition(" userGroupId = ? ", groupId);
    }
}
