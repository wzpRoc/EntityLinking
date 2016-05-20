package org.ailab.tem.wim.userGroup;

import org.ailab.tem.DBConfig;
import org.ailab.tem.wim.user.User;
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
 * 功能描述：用户组业务逻辑
 */
public class UserGroupDAO extends BaseDAO {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(UserGroupDAO.class);

    public UserGroupDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }


    /**
     * 得到表名
     *
     * @return 表名
     */
    protected String getTableName() {
        return "userGroup";
    }


    /**
     * 得到查询一条记录的SQL
     *
     * @return 查询一条记录的SQL
     */
    protected String getSqlForGet() {
        return "select * from userGroup where id = ?";
    }


    /**
     * 得到查询多条记录的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForGetList() {
        return "SELECT * FROM userGroup";
    }


    /**
     * 得到查询记录总数的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForCount() {
        return "SELECT count(*) FROM userGroup";
    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete(String keys) {
        return "DELETE FROM userGroup WHERE id in (" + keys + ")";
    }


    /**
     * 将resultSet中的值设置到dto中
     *
     * @param res
     * @return dto
     * @throws java.sql.SQLException
     */
    protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
        UserGroup dto = new UserGroup();

        dto.setId(res.getInt("id"));
        dto.setName(res.getString("name"));
        dto.setCategory(res.getInt("category"));
        dto.setUserCount(res.getInt("userCount"));
        dto.setDescription(res.getString("description"));


        return dto;
    }


    /**
     * 得到insert语句的SQL
     *
     * @return insert语句的SQL
     */
    protected String getSqlForInsert() {
        return "INSERT INTO userGroup (id, name, category, userCount, description)VALUES(?, ?, ?, ?, ?)";
    }


    /**
     * 为insert设置参数
     */
    protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
        UserGroup dto = (UserGroup) baseDTO;

        paramList.add(dto.getId());
        paramList.add(dto.getName());
        paramList.add(dto.getCategory());
        paramList.add(dto.getUserCount());
        paramList.add(dto.getDescription());

    }


    /**
     * 得到update语句的SQL
     *
     * @return update语句的SQL
     */
    protected String getSqlForUpdate() {
        return "UPDATE userGroup SET name = ?, category = ?, userCount = ?, description = ? WHERE id = ?";
    }


    /**
     * 为update设置参数
     */
    protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
        UserGroup dto = (UserGroup) baseDTO;

        paramList.add(dto.getName());
        paramList.add(dto.getCategory());
        paramList.add(dto.getUserCount());
        paramList.add(dto.getDescription());

        paramList.add(dto.getId());

    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete() {
        return "DELETE FROM userGroup WHERE id = ?";
    }


    /**
     * 由id得到userGroup
     *
     * @param id id
     */
    public UserGroup get(int id) throws SQLException {
        UserGroup userGroup;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        userGroup = (UserGroup) getBySql("select * from userGroup where id = ?", paramList);
        return userGroup;
    }


}
