package org.ailab.tem.wim.menu;

import org.ailab.tem.DBConfig;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-28
 * 功能描述：菜单业务逻辑
 */
public class MenuDAO extends BaseDAO {
    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(MenuDAO.class);


    public MenuDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }


    /**
     * 得到表名
     *
     * @return 表名
     */
    protected String getTableName() {
        return "menu";
    }


    /**
     * 得到查询一条记录的SQL
     *
     * @return 查询一条记录的SQL
     */
    protected String getSqlForGet() {
        return "select * from menu where id = ?";
    }


    /**
     * 得到查询多条记录的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForGetList() {
        return "SELECT * FROM menu";
    }


    /**
     * 得到查询记录总数的SQL
     *
     * @return 查询多条记录的SQL
     */
    protected String getSqlForCount() {
        return "SELECT count(*) FROM menu";
    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete(String keys) {
        return "DELETE FROM menu WHERE id in (" + keys + ")";
    }


    /**
     * 将resultSet中的值设置到dto中
     *
     * @param res
     * @return dto
     * @throws java.sql.SQLException
     */
    protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
        Menu dto = new Menu();

        dto.setId(res.getInt("id"));
        dto.setParentId(res.getInt("parentId"));
        dto.setName(res.getString("name"));
        dto.setLink(res.getString("link"));
        dto.setModuleName(res.getString("moduleName"));
        dto.setDowhat(res.getString("dowhat"));
        dto.setLevel(res.getInt("level"));
        dto.setSiblingSeq(res.getInt("siblingSeq"));
        dto.setDescription(res.getString("description"));


        return dto;
    }


    /**
     * 得到insert语句的SQL
     *
     * @return insert语句的SQL
     */
    protected String getSqlForInsert() {
        return "INSERT INTO menu (id, parentId, name, link, moduleName, dowhat, level, siblingSeq, description)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }


    /**
     * 为insert设置参数
     */
    protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
        Menu dto = (Menu) baseDTO;

        paramList.add(dto.getId());
        paramList.add(dto.getParentId());
        paramList.add(dto.getName());
        paramList.add(dto.getLink());
        paramList.add(dto.getModuleName());
        paramList.add(dto.getDowhat());
        paramList.add(dto.getLevel());
        paramList.add(dto.getSiblingSeq());
        paramList.add(dto.getDescription());

    }


    /**
     * 得到update语句的SQL
     *
     * @return update语句的SQL
     */
    protected String getSqlForUpdate() {
        return "UPDATE menu SET parentId = ?, name = ?, link = ?, moduleName = ?, dowhat = ?, level = ?, siblingSeq = ?, description = ? WHERE id = ?";
    }


    /**
     * 为update设置参数
     */
    protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
        Menu dto = (Menu) baseDTO;

        paramList.add(dto.getParentId());
        paramList.add(dto.getName());
        paramList.add(dto.getLink());
        paramList.add(dto.getModuleName());
        paramList.add(dto.getDowhat());
        paramList.add(dto.getLevel());
        paramList.add(dto.getSiblingSeq());
        paramList.add(dto.getDescription());

        paramList.add(dto.getId());

    }


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    protected String getSqlForDelete() {
        return "DELETE FROM menu WHERE id = ?";
    }


    /**
     * 由id得到menu
     *
     * @param id id
     */
    public Menu get(int id) throws SQLException {
        Menu menu;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        menu = (Menu) getBySql("select * from menu where id = ?", paramList);

        return menu;
    }


    protected List<Menu> getMenuListByGroupIds(String groupIds) throws SQLException {
        return getList(
                "SELECT DISTINCT m.* " +
                        "FROM " + getTableName() + " m " +
                        "JOIN userGroupMenu gm on gm.menuId = m.id " +
                        "WHERE gm.userGroupId IN (" + groupIds + ")");
    }


    protected List<Menu> getMenuListByMenuIds(String menuIds) throws SQLException {
        List<Menu> menuList = getListByCondition("id IN (" + menuIds + ")");
        return menuList;
    }
}
