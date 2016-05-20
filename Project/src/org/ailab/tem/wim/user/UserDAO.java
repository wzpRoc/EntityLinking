package org.ailab.tem.wim.user;

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
 * 创建日期: 2013-12-10
 * 功能描述：用户业务逻辑
 */
class UserDAO extends BaseDAO {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(UserDAO.class);


    public UserDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }

    /**
     * 得到表名
     *
     * @return 表名
     */
    protected String getTableName() {
        return "user";
    }


    /**
     * 得到查询一条记录的SQL
     *
     * @return 查询一条记录的SQL
     */
    protected String getSqlForGet() {
        return "select * from user where id = ?";
    }


    /**
     * 得到查询多条记录的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForGetList() {
        return "SELECT * FROM user";
    }


    /**
     * 得到查询记录总数的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForCount() {
        return "SELECT count(*) FROM user";
    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete(String keys) {
        return "DELETE FROM user WHERE id in (" + keys + ")";
    }


    /**
     * 将resultSet中的值设置到dto中
     *
     * @param res
     * @return dto
     * @throws java.sql.SQLException
     */
    protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
        User dto = new User();

        dto.setId(res.getInt("id"));
        dto.setUsername(res.getString("username"));
        dto.setPassword(res.getString("password"));
        dto.setNicName(res.getString("nicName"));
        dto.setRealName(res.getString("realName"));
        dto.setRole(res.getInt("role"));
        dto.setCustomerType(res.getInt("customerType"));
        dto.setPoints(res.getInt("points"));
        dto.setGrade(res.getInt("grade"));
        dto.setGradePoint(res.getInt("gradePoint"));
        dto.setRegisterTime(res.getString("registerTime"));
        if(res.getString("lastLoginTime") != null)
            dto.setLastLoginTime(res.getString("lastLoginTime").substring(0, 19));
        dto.setEmail(res.getString("email"));
        dto.setMobilephone(res.getString("mobilephone"));
        dto.setTelephone(res.getString("telephone"));
        dto.setPhotoUrl(res.getString("photoUrl"));
        dto.setSex(res.getInt("sex"));
        dto.setBirthday(res.getString("birthday"));
        dto.setAddress(res.getString("address"));
        dto.setHobby(res.getString("hobby"));
        dto.setCarType(res.getString("carType"));
        dto.setCarPurchaseDate(res.getString("carPurchaseDate"));
        dto.setDegree(res.getInt("degree"));
        dto.setCertificateType(res.getInt("certificateType"));
        dto.setCertificateNo(res.getString("certificateNo"));
        dto.setDeviceTag(res.getInt("deviceTag"));
        dto.setState(res.getInt("state"));
        dto.setSendValidationTime(res.getString("sendValidationTime"));
        dto.setAuthenticode(res.getString("authenticode"));


        return dto;
    }


    /**
     * 得到insert语句的SQL
     *
     * @return insert语句的SQL
     */
    protected String getSqlForInsert() {
        return "INSERT INTO user (id, username, password, nicName, realName, role, customerType, grade, gradePoint, registerTime, lastLoginTime, email, mobilephone, telephone, photoUrl, sex, birthday, address, hobby, carType, carPurchaseDate, degree, certificateType, certificateNo, deviceTag, state, sendValidationTime, authenticode)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }


    /**
     * 为insert设置参数
     */
    protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
        User dto = (User) baseDTO;

        paramList.add(dto.getId());
        paramList.add(dto.getUsername());
        paramList.add(dto.getPassword());
        paramList.add(dto.getNicName());
        paramList.add(dto.getRealName());
        paramList.add(dto.getRole());
        paramList.add(dto.getCustomerType());
        paramList.add(dto.getGrade());
        paramList.add(dto.getGradePoint());
        paramList.add("".equals(dto.getRegisterTime()) ? null : dto.getRegisterTime());
        paramList.add("".equals(dto.getLastLoginTime()) ? null : dto.getLastLoginTime());
        paramList.add(dto.getEmail());
        paramList.add(dto.getMobilephone());
        paramList.add(dto.getTelephone());
        paramList.add(dto.getPhotoUrl());
        paramList.add(dto.getSex());
        paramList.add("".equals(dto.getBirthday()) ? null : dto.getBirthday());
        paramList.add(dto.getAddress());
        paramList.add(dto.getHobby());
        paramList.add(dto.getCarType());
        paramList.add("".equals(dto.getCarPurchaseDate()) ? null : dto.getCarPurchaseDate());
        paramList.add(dto.getDegree());
        paramList.add(dto.getCertificateType());
        paramList.add(dto.getCertificateNo());
        paramList.add(dto.getDeviceTag());
        paramList.add(dto.getState());
        paramList.add("".equals(dto.getSendValidationTime()) ? null : dto.getSendValidationTime());
        paramList.add(dto.getAuthenticode());

    }


    /**
     * 得到update语句的SQL
     *
     * @return update语句的SQL
     */
    protected String getSqlForUpdate() {
        return "UPDATE user SET username = ?, password = ?, nicName = ?, realName = ?, role = ?, customerType = ?, grade = ?, gradePoint = ?, registerTime = ?, lastLoginTime = ?, email = ?, mobilephone = ?, telephone = ?, photoUrl = ?, sex = ?, birthday = ?, address = ?, hobby = ?, carType = ?, carPurchaseDate = ?, degree = ?, certificateType = ?, certificateNo = ?, deviceTag = ?, state = ?, sendValidationTime = ?, authenticode = ? WHERE id = ?";
    }


    /**
     * 为update设置参数
     */
    protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
        User dto = (User) baseDTO;

        paramList.add(dto.getUsername());
        paramList.add(dto.getPassword());
        paramList.add(dto.getNicName());
        paramList.add(dto.getRealName());
        paramList.add(dto.getRole());
        paramList.add(dto.getCustomerType());
        paramList.add(dto.getGrade());
        paramList.add(dto.getGradePoint());
        paramList.add("".equals(dto.getRegisterTime()) ? null : dto.getRegisterTime());
        paramList.add("".equals(dto.getLastLoginTime()) ? null : dto.getLastLoginTime());
        paramList.add(dto.getEmail());
        paramList.add(dto.getMobilephone());
        paramList.add(dto.getTelephone());
        paramList.add(dto.getPhotoUrl());
        paramList.add(dto.getSex());
        paramList.add("".equals(dto.getBirthday()) ? null : dto.getBirthday());
        paramList.add(dto.getAddress());
        paramList.add(dto.getHobby());
        paramList.add(dto.getCarType());
        paramList.add("".equals(dto.getCarPurchaseDate()) ? null : dto.getCarPurchaseDate());
        paramList.add(dto.getDegree());
        paramList.add(dto.getCertificateType());
        paramList.add(dto.getCertificateNo());
        paramList.add(dto.getDeviceTag());
        paramList.add(dto.getState());
        paramList.add("".equals(dto.getSendValidationTime()) ? null : dto.getSendValidationTime());
        paramList.add(dto.getAuthenticode());

        paramList.add(dto.getId());

    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete() {
        return "DELETE FROM user WHERE id = ?";
    }


    /**
     * 由id得到user
     *
     * @param id id
     */
    public User get(int id) throws SQLException {
        User user;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        user = (User) getBySql("select * from user where id = ?", paramList);

        return user;
    }


    /**
     * 由用户名、密码得到user
     */
    public User getByUsernameAndPassword(String username, String password) throws SQLException {
        User user;
        user = (User) get("select * from user where username = ? and password = ?", username, password);

        return user;
    }

    public List<User> getUserListByUserIds(String userIds) throws SQLException {
        List<User> userList = getListByCondition("id IN (" + userIds + " )");
        return userList;
    }

}
