package org.ailab.wimfra.core;


import org.ailab.wimfra.core.idFactory.IdFactory;
import org.ailab.wimfra.database.DBConnect;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.*;
import org.ailab.wimfra.util.time.TimeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: business logic base class
 */
public abstract class BaseDAO {
    // nextId未被初始化时的赋值
    private static final int UNINITIALIZED_NEXT_ID = Integer.MIN_VALUE;
    protected String tableName;
    protected String tableAlias;
    protected String sqlForGetList;
    protected String sqlForCount;
    protected String dbConnectConfig;

    {
        tableName = getTableName();
        tableAlias = getTableAlias();
        sqlForGetList = getSqlForGetList();
        sqlForCount = getSqlForCount();
    }

    /**
     * 日志工具
     */
    protected Logger logger = Logger.getLogger(this.getClass());

    /**
     * 数据库连接
     */
    protected DBConnect dbConnect;

    /**
     * 是否使用自己的数据库连接
     */
    protected boolean isOwnDBConnect = true;

    /**
     * 是否是批量操作模式
     */
    protected boolean isBatchMode = false;

    /**
     * 下一个ID
     */
    protected int nextId = UNINITIALIZED_NEXT_ID;

    // 默认的表别名
    protected String defaultTableAlias;

    /**
     * 得到查询多条记录的SQL
     *
     * @return 查询多条记录的SQL
     */
    abstract protected String getSqlForGetList();


    /**
     * 得到查询一条记录的SQL
     *
     * @return 查询一条记录的SQL
     */
    abstract protected String getSqlForGet();


    /**
     * 得到表名
     *
     * @return 表名
     */
    abstract protected String getTableName();


    /**
     * 得到备份表名
     *
     * @return 备份表名
     */
    protected String getBackupTableName(boolean withTime) {
        if (withTime) {
            return getTableName() + "_" + TimeUtil.getDateTime();
        } else {
            return getTableName() + "_bak";
        }
    }


    /**
     * 得到历史表表名
     *
     * @return 历史表表名
     */
/*
    protected String getHistoryTableName() {
        return getTableName() + "_history";
    }
*/


    /**
     * 将resultSet中的值设置到dto中
     *
     * @param res
     * @return dto
     * @throws java.sql.SQLException
     */
    abstract protected BaseDTO resultSetToDto(ResultSet res) throws SQLException;


    /**
     * 得到insert语句的SQL
     *
     * @return insert语句的SQL
     */
    abstract protected String getSqlForInsert();


    /**
     * 为insert设置参数
     */
    abstract protected void setParametersForInsert(List<Object> list, BaseDTO baseDTO);


    /**
     * 得到update语句的SQL
     *
     * @return update语句的SQL
     */
    abstract protected String getSqlForUpdate();


    /**
     * 为update设置参数
     *
     * @param list
     * @param baseDTO
     */
    abstract protected void setParametersForUpdate(List<Object> list, BaseDTO baseDTO);


    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    abstract protected String getSqlForDelete();

    /**
     * 得到delete语句的SQL
     *
     * @return delete语句的SQL
     */
    abstract protected String getSqlForDelete(String keys);


    protected Class getDtoClass() throws Exception {
        throw new Exception("Method has not been implenmented: getDtoClass()");
    }

    /**
     * 默认的表别名
     *
     * @return
     */
    protected String getTableAlias() {
        String tableName = getTableName();
        String tableAlias = tableName.substring(0, 1);
        for (int i = 1; i < tableName.length(); i++) {
            char c = tableName.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                tableAlias += Character.toLowerCase(c);
            }
        }

        return tableAlias;
    }


    /**
     * 默认的排序方式
     *
     * @return
     */
    protected String getDefaultOrderBy() {
        return null;
    }

    /**
     * 为delete设置参数
     */
    protected void setParametersForDelete(List<Object> list, Object key) {
        list.add(key);
    }


    /**
     * 得到计数sql
     */
    protected String getSqlForCount() {
        return "SELECT COUNT(*) FROM " + tableName + " " + tableAlias;
    }


    /**
     * 得到计数sql
     *
     * @param tableName
     */
    protected String getSqlForCount(String tableName, String joinSql, String whereCondition) {
        String s = "SELECT COUNT(*) FROM " + tableName;
        if (tableName.contains(" ") || tableName.contains("\t")) {
            // do not need tableAlias
        } else {
            s = s + " " + tableAlias;
        }

        if (joinSql != null) {
            s += " " + joinSql;
        }

        if (whereCondition != null) {
            s += " " + whereCondition;
        }
        return s;
    }


    /**
     * 按照给定的sql查询一条记录
     */
    public BaseDTO getBySql(String sql, List<Object> paramList) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, paramList);

            if (res.next()) {
                return resultSetToDto(res);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询一条记录
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public BaseDTO get(String sql, Object... params) {
        return get(null, sql, params);
    }


    public BaseDTO get(ResultSetToDtoAdapter resultSetToDtoAdapter, String sql, Object... params) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);

            if (res.next()) {
                if (resultSetToDtoAdapter == null) {
                    return resultSetToDto(res);
                } else {
                    return resultSetToDtoAdapter.resultSetToDto(res);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询一个值
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public String getString(String sql, Object... params) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);

            if (res.next()) {
                return res.getString(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询一个值
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public int getInt(String sql, Object... params) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);

            if (res.next()) {
                return res.getInt(1);
            } else {
                return Integer.MIN_VALUE;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询一个值
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public List<Integer> getIntListInARow(String sql, Object... params) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);
            int columnCount = res.getMetaData().getColumnCount();

            if (res.next()) {
                List<Integer> list = new ArrayList<Integer>(columnCount);
                for (int i = 0; i < columnCount; i++) {
                    list.add(res.getInt(i + 1));
                }
                return list;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询一个值
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public List<List<Integer>> getIntListList(String sql, Object... params) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);
            int columnCount = res.getMetaData().getColumnCount();

            List<List<Integer>> listList = new ArrayList<List<Integer>>();
            while (res.next()) {
                List<Integer> list = new ArrayList<Integer>(columnCount);
                listList.add(list);
                for (int i = 0; i < columnCount; i++) {
                    list.add(res.getInt(i + 1));
                }
            }

            return listList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的键值查询一条记录
     *
     * @param key
     * @return Dto
     * @throws java.sql.SQLException
     */
    public BaseDTO getById(Object key) {
        BaseDTO dto;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(key);
        String sqlForGet = getSqlForGet();
        dto = getBySql(sqlForGet, paramList);

        return dto;
    }


    /**
     * 按照给定的sql查询多条记录
     */
    public ArrayList getListBySql(String sql, List<Object> paramList) {
        ResultSet res = null;
        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, paramList);

            ArrayList<BaseDTO> list = new ArrayList<BaseDTO>();
            int i = 0;
            while (res.next()) {
                list.add(resultSetToDto(res));
                i++;
                if (i % 10000 == 0) {
                    logger.debug(i + " rows loaded");
                }
            }

            logger.debug(list.size() + " records loaded.");
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询多条记录
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public ArrayList getListBySqlAndParams(String sql, Object... params) {
        ResultSet res = null;
        try {
            logger.debug(sql);
            Stopwatch sw = new Stopwatch();

            res = getDBConnect().executeQuery(sql, params);

            ArrayList<BaseDTO> list = new ArrayList<BaseDTO>();
            int i = 0;
            while (res.next()) {
                list.add(resultSetToDto(res));
                i++;
                if (i % 10000 == 0) {
                    logger.debug(i + " records loaded.");
                }
            }

            sw.stop();
            logger.debug(list.size() + " records loaded. Time: " + sw.getSeconds() + " seconds.");
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询多条记录
     */
    public ArrayList getListBySql(String sql, List<Object> paramList, boolean enableFieldMatching) {
        ResultSet res = null;
        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, paramList);

            ArrayList<BaseDTO> list = new ArrayList<BaseDTO>();
            while (res.next()) {
                if (enableFieldMatching) {
                    list.add(resultSetToDto(res));
                } else {
                    list.add(resultSetToDto(res));
                }
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询多条记录
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public ArrayList getList(String sql, Object... params) {
        ResultSet res = null;
        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            ArrayList<BaseDTO> list = new ArrayList<BaseDTO>();
            while (res.next()) {
                list.add(resultSetToDto(res));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询多条记录
     */
    public List<Integer> getIntList(String sql, Object... params) {
        ResultSet res = null;
        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            List<Integer> list = new ArrayList<Integer>();
            while (res.next()) {
                list.add(res.getInt(1));
            }

            //    logger.debug(list.size()+" int loaded.");

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }

    /**
     * 按照给定的sql查询多条记录
     */
    public Set<Integer> getIntSet(String sql, Object... params) {
        ResultSet res = null;
        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            Set<Integer> set = new HashSet<Integer>();
            while (res.next()) {
                set.add(res.getInt(1));
            }

            //    logger.debug(list.size()+" int loaded.");

            return set;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }

    /**
     * 按照给定的sql查询多条记录
     *
     * @param sql
     * @param paramCollection
     * @return Dto
     * @throws java.sql.SQLException
     */
    public ArrayList getListByValueCollection(String sql, Collection paramCollection) {
        ResultSet res = null;
        try {
            //logger.debug(sql);
            res = getDBConnect().executeQuery(sql, paramCollection.toArray());

            ArrayList<BaseDTO> list = new ArrayList<BaseDTO>();
            while (res.next()) {
                list.add(resultSetToDto(res));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询多条记录
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public ArrayList getListUsingFieldMatching(String sql, Object... params) throws Exception {
        return getListUsingClass(sql, getDtoClass(), params);
    }


    /**
     * 按照给定的sql查询多条记录
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public ArrayList getListUsingClass(String sql, Class clazz, Object... params) throws Exception {
        ResultSet res = null;
        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            // 对要填充的类，得到名称到字段的映射表
            HashMap<String, Field> nameToFieldMap = ClassUtil.getNameToFieldMap(clazz);

            // 对sql，检查是否所有列都能找到相应的字段
            int colNum = res.getMetaData().getColumnCount();
            Field[] fieldsOrderedByColumn = new Field[colNum];
            for (int i = 0; i < colNum; i++) {
                String colName = res.getMetaData().getColumnName(i + 1).toLowerCase();
                fieldsOrderedByColumn[i] = nameToFieldMap.get(colName);
            }

            ArrayList list = new ArrayList();
            while (res.next()) {
                Object obj = clazz.newInstance();
                for (int i = 0; i < colNum; i++) {
                    Field field = fieldsOrderedByColumn[i];
                    if (field == null) continue;
                    DBUtil.setFieldValue(res, i + 1, obj, field);
                }
                list.add(obj);
            }

            logger.debug("finished");
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(DBUtil.substituteParameter(sql, params));
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照给定的sql查询多条记录
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public Object getBySQLAndClass(String sql, Class clazz, Object... params) throws Exception {
        ResultSet res = null;
        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            // 对要填充的类，得到名称到字段的映射表
            HashMap<String, Field> nameToFieldMap = ClassUtil.getNameToFieldMap(clazz);

            // 对sql，检查是否所有列都能找到相应的字段
            int colNum = res.getMetaData().getColumnCount();
            Field[] fieldsOrderedByColumn = new Field[colNum];
            for (int i = 0; i < colNum; i++) {
                String colName = res.getMetaData().getColumnName(i + 1).toLowerCase();
                fieldsOrderedByColumn[i] = nameToFieldMap.get(colName);
            }

            if (res.next()) {
                Object obj = clazz.newInstance();
                for (int i = 0; i < colNum; i++) {
                    Field field = fieldsOrderedByColumn[i];
                    if (field == null) continue;
                    DBUtil.setFieldValue(res, i + 1, obj, field);
                }
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(DBUtil.substituteParameter(sql, params));
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }

    /**
     * 是否存在查询结果
     *
     * @param sql
     * @param params
     * @return Dto
     * @throws java.sql.SQLException
     */
    public boolean exists(String sql, Object... params) {
        ResultSet res = null;
        try {
            //logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            return res.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 查询有限条记录
     */
    public List getLimitedList(int limit) {
        return getListBySql(getSqlForGetList() + " LIMIT " + limit, null);
    }

    /**
     * 按照给定的条件查询多条记录
     *
     * @param condition
     * @return ArrayList<DtoBase>
     */
    public List getListByCondition(String condition) {
        return getListBySql(getSqlForGetList() + " WHERE " + condition, null);
    }


    /**
     * 按照给定的条件查询多条记录
     *
     * @param condition
     * @return ArrayList<DtoBase>
     * @throws java.sql.SQLException
     */
    public ArrayList getListByCondition(String condition, Object... paras) {
        String sql = getSqlForGetList();
        if (condition != null) {
            sql += " WHERE " + condition;
        }

        return getListBySqlAndParams(sql, paras);
    }


    public BaseDTO getByCondition(String condition, Object... paras) {
        return get(getSqlForGetList() + " WHERE " + condition, paras);
    }


    /**
     * 查询所有记录
     *
     * @return ArrayList<DtoBase>
     * @throws java.sql.SQLException
     */
    public List getList() {
        String defaultOrderBy = getDefaultOrderBy();
        String sql = getSqlForGetList();
        if (StringUtil.notEmpty(defaultOrderBy)) {
            sql += "\nORDER BY " + defaultOrderBy;
        }
        return getListBySql(sql, null);
    }


    /**
     * 得到多条记录
     *
     * @param page
     * @return Page
     * @throws java.sql.SQLException
     */
    public Page getList(Page page) throws Exception {
        ResultSet res = null;

        try {
            // 得到总记录数

            // 查询条件
            String whereCondition;
            if (page.getWhereCondition() == null || "".equals(page.getWhereCondition())) {
                whereCondition = null;
            } else {
                whereCondition = "\nWHERE (" + page.getWhereCondition() + ")";
            }

            // 处理idList
            final boolean enableIdList = page.getIdValueList() != null;
            if (enableIdList) {
                // 启用idList
                // 查询条件
                String idCondition = "(" + page.getIdFieldName() + " in (" + StringUtil.listToString(page.idValueList, ",") + "))";
                // 完整的查询条件 
                if (whereCondition == null) {
                    whereCondition = "\nWHERE " + idCondition;
                } else {
                    whereCondition += "\nAND " + idCondition;
                }
            }

            // 表名
            final String tableName = page.getTableName() == null
                    ? getTableName()
                    : page.getTableName();

            // 表连接处理
            String joinSql = getJoinSql(page);

            // 计数
            String sqlForCount = page.getSqlForCount();
            if (sqlForCount == null) {
                sqlForCount = getSqlForCount(tableName, joinSql, whereCondition);
            }
            logger.debug(sqlForCount);
            res = getDBConnect().executeQuery(sqlForCount);
            res.next();
            int totalRecordNum = res.getInt(1);
            res.close();
            page.setTotalRecordNum(totalRecordNum);

            // 如果当前页码大于总页码，则当前页码设为总页码
            if (page.getCurrentPageNo() > page.getTotalPageNum()) {
                page.setCurrentPageNo(page.getTotalPageNum());
            }

            // 检查每页记录大小，上线为200
            if (page.getRecordNumPerPage() > Page.MAXRECORDNUMPERPAGE)
                page.setRecordNumPerPage(Page.MAXRECORDNUMPERPAGE);
            // 取记录
            int start = page.getRecordNumPerPage() * (page.getCurrentPageNo() - 1);
            StringBuffer sb_sqlForGetList = new StringBuffer();

            if (page.getSqlForGetList() != null) {
                sb_sqlForGetList.append(page.getSqlForGetList());
            } else {
                // 构造查询语句
                if (page.getFileds() == null || "".equals(page.getFileds())) {
                    sb_sqlForGetList.append(getSqlForGetList());
                } else {
                    // 指定了查询字段
                    sb_sqlForGetList.append("SELECT");
                    sb_sqlForGetList.append(" ").append(page.getFileds());
                    sb_sqlForGetList.append("\nFROM ").append(tableName);
                    if (StringUtil.notEmpty(getTableAlias())) {
                        sb_sqlForGetList.append(" ").append(getTableAlias());
                    }
                }
            }

            // 表连接
            if (joinSql != null) {
                sb_sqlForGetList.append(" ").append(joinSql);
            }

            // 查询条件
            if (whereCondition != null) {
                sb_sqlForGetList.append(whereCondition);
            }

            // 排序
            String orderBy = page.getOrderBy();
            if (orderBy == null || "".equals(orderBy)) {
                // 使用bl中设置的默认的排序方式
                orderBy = getDefaultOrderBy();
            }
            if (orderBy != null && !"".equals(orderBy)) {
                sb_sqlForGetList.append("\nORDER BY ").append(orderBy);
                if (StringUtil.notEmpty(page.order)) {
                    sb_sqlForGetList.append(" ").append(page.order);
                }
            }

            // 分页
            sb_sqlForGetList
                    .append(" LIMIT ")
                    .append(start).append(", ").append(page.getRecordNumPerPage());

            final String sql = sb_sqlForGetList.toString();
            logger.debug(sql);

            res = getDBConnect().executeQuery(sql);
            ArrayList<BaseDTO> list = new ArrayList<BaseDTO>();
            while (res.next()) {
                list.add(resultSetToDto(res));
            }

            // 按照idList排序
            if (enableIdList) {
                list = sortByIdList(list, page.getIdFieldName(), page.idValueList);
            }

            page.setList(list);

            return page;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 获得表连接SQL
     */
    private String getJoinSql(Page page) {
        if (page.getJoinConfigList() == null || page.getJoinConfigList().size() == 0) {
            return null;
        }

        StringBuilder joinSql = new StringBuilder();
        for (JoinConfig joinConfig : page.getJoinConfigList()) {
            // 表名
            joinSql.append(" JOIN ").append(joinConfig.getTableName());
            // 别名
            if (StringUtil.notEmpty(joinConfig.getTableAlias())) {
                joinSql.append(" ").append(joinConfig.getTableAlias());
            }
            // 条件
            if (StringUtil.notEmpty(joinConfig.getCondition())) {
                joinSql.append(" ON (").append(joinConfig.getCondition()).append(")");
            }
        }

        return joinSql.toString();
    }


    /**
     * 得到多条记录
     *
     * @throws java.sql.SQLException
     */
    public List getListByIds(String ids) throws Exception {
        return getList(CollectionUtil.stringToList(ids, ","), "*");
    }


    /**
     * 得到多条记录
     *
     * @throws java.sql.SQLException
     */
    public List getList(Collection idCollection) {
        return getList(idCollection, "*");
    }


    /**
     * 得到多条记录
     *
     * @throws java.sql.SQLException
     */
    public List getList(Collection idCollection, String fields) {
        ResultSet res = null;

        try {
            if (idCollection == null || idCollection.size() == 0) {
                return new ArrayList();
            }

            // 查询条件
            String idCondition = "\nWHERE (id in (" + StringUtil.collectionToString(idCollection, ",") + "))";

            // 取记录
            StringBuffer sqlForGetList = new StringBuffer();

            // 构造查询语句
            sqlForGetList.append("SELECT");

            // 查询字段
            sqlForGetList.append(" ");
            sqlForGetList.append(fields);
            sqlForGetList.append("\nFROM ").append(getTableName());
            sqlForGetList.append(idCondition);

            res = getDBConnect().executeQuery(sqlForGetList.toString());
            ArrayList<BaseDTO> list = new ArrayList<BaseDTO>();
            while (res.next()) {
                list.add(resultSetToDto(res));
            }

            // 按照idList排序
            list = sortByIdList(list, "id", idCollection);

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 按照id排序
     * 只支持一对一
     *
     * @param list
     * @param idFieldName
     * @param idList
     * @return
     * @throws NoSuchMethodException
     * @throws java.lang.reflect.InvocationTargetException
     *
     * @throws IllegalAccessException
     */
    private ArrayList<BaseDTO> sortByIdList(ArrayList<BaseDTO> list, String idFieldName, Collection idList) {
        if (idList == null) {
            return null;
        }

        ArrayList<BaseDTO> listAfterSort = new ArrayList<BaseDTO>(list.size());

        // 得到id到dto的映射
        HashMap<Object, BaseDTO> idToDtoMap = new HashMap<Object, BaseDTO>(list.size());
        String idFieldName_ui = idFieldName.substring(0, 1).toUpperCase();
        if (idFieldName.length() > 1) {
            idFieldName_ui += idFieldName.substring(1);
        }
        String getMethodName = "get" + idFieldName_ui;
        try {
            for (BaseDTO dto : list) {
                Object idValue = dto.getClass().getMethod(getMethodName).invoke(dto);
                idToDtoMap.put(idValue, dto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 排序
        for (Object idValue : idList) {
            BaseDTO dto = idToDtoMap.get(idValue);
            if (dto != null) {
                listAfterSort.add(dto);
            }
        }

        return listAfterSort;
    }


    /**
     * 获得总记录数
     *
     * @return count
     * @throws java.sql.SQLException
     */
    public int getCount() {
        ResultSet res = null;

        try {
            String sqlForCount = getSqlForCount(getTableName(), null, null);
            res = getDBConnect().executeQuery(sqlForCount);
            res.next();

            return res.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 获得记录数
     *
     * @return count
     * @throws java.sql.SQLException
     */
    public int getCount(String condition, Object... params) {
        ResultSet res = null;

        try {
            String sqlForCount = getSqlForCount(getTableName(), null, " WHERE " + condition);
            res = getDBConnect().executeQuery(sqlForCount, params);
            res.next();

            return res.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException e) {
                    logger.error(e);
                }
            }
            closeDBConnect();
        }
    }


    /**
     * 得到字符串集合
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public Set<String> getStringSet(String sql, Object... params) {
        List<String> list = getStringList(sql, params);
        if(list == null) {
            return null;
        }
        return new HashSet<String>(list);
    }

    /**
     * 得到字符串数组
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public String[] getStrings(String sql, Object... params) {
        List<String> list = getStringList(sql, params);
        if(list == null) {
            return null;
        }
        String[] ss = new String[list.size()];
        for(int i=0; i<list.size(); i++) {
            ss[i] = list.get(i);
        }

        return ss;
    }

    /**
     * 得到字符串列表
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public List<String> getStringList(String sql, Object... params) {
        ResultSet res = null;
        DBConnect dbc = null;

        try {
            dbc = getDBConnect();
            logger.debug(sql);
            res = dbc.executeQuery(sql, params);

            boolean isVarbinary = "VARBINARY".equalsIgnoreCase(res.getMetaData().getColumnTypeName(1));

            List<String> list = new ArrayList<String>();
            int i = 0;
            while (res.next()) {
                i++;
                if (i % 10000 == 0) {
                    logger.debug(i + " loaded");
                }
                String s = isVarbinary
                        ? new String(res.getBytes(1))
                        : res.getString(1);
                if (s != null) {
                    s = s.trim();
                }
                list.add(s);
            }

            logger.debug(i + " loaded");
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dbc != null) {
                dbc.close();
            }
        }
    }


    /**
     * 得到字符串列表
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public List<String[]> getStringsList(String sql, Object... params) {
        ResultSet res = null;
        DBConnect dbc = null;

        try {
            dbc = getDBConnect();
            logger.debug(sql);
            res = dbc.executeQuery(sql, params);

            final int nrColumn = res.getMetaData().getColumnCount();

            List<String[]> list = new ArrayList<String[]>();
            int i = 0;
            while (res.next()) {
                i++;
                if (i % 10000 == 0) {
                    logger.debug(i + " loaded");
                }

                String[] as = new String[nrColumn];
                for (int iColumn = 0; iColumn < nrColumn; iColumn++) {
                    String s = res.getString(iColumn + 1);
                    if (s != null) {
                        s = s.trim();
                    }
                    as[iColumn] = s;
                }
                list.add(as);
            }

            logger.debug(i + " loaded");
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dbc != null) {
                dbc.close();
            }
        }
    }


    /**
     * 得到Map，其中查询的第一个字段为键，第二个字段为值
     */
    public static HashMap<String, String> getMap(String sql) {
        ResultSet res = null;
        DBConnect dbc = null;

        try {
            dbc = new DBConnect();
            res = dbc.executeQuery(sql);

            HashMap<String, String> map = new HashMap<String, String>();
            while (res.next()) {
                map.put(res.getString(1), res.getString(2));
            }

            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dbc != null) {
                dbc.close();
            }
        }
    }


    /**
     * 得到Map，其中查询的第一个字段为键，第二个字段为值
     */
    public static HashMap<Integer, Integer> getIntToIntMap(String sql, Object... params) {
        ResultSet res = null;
        DBConnect dbc = null;

        try {
            dbc = new DBConnect();
            res = dbc.executeQuery(sql, params);

            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            while (res.next()) {
                map.put(res.getInt(1), res.getInt(2));
            }

            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dbc != null) {
                dbc.close();
            }
        }
    }


    /**
     * 得到Map，其中查询的第一个字段为键，第二个字段为值
     */
    public HashMap<String, Integer> getStringToIntMap(String sql, Object... params) {
        ResultSet res = null;
        DBConnect dbc = null;

        try {
            dbc = new DBConnect();
            res = dbc.executeQuery(sql, params);

            HashMap<String, Integer> map = new HashMap<String, Integer>();
            while (res.next()) {
                map.put(res.getString(1), res.getInt(2));
            }

            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (dbc != null) {
                dbc.close();
            }
        }
    }


    /**
     * 插入一条记录
     */
    public int insert(BaseDTO dto) {
        List<Object> paramList = new ArrayList<Object>();
        setParametersForInsert(paramList, dto);
        return executeUpdate(getSqlForInsert(), paramList);
    }


    /**
     * 插入多条记录
     */
    public int batchInsert(Collection collection) throws Exception {
        if (collection == null) return 0;

        List<List<Object>> paraList = new ArrayList<List<Object>>();
        for (Object obj : collection) {
            BaseDTO dto = (BaseDTO) obj;
            List<Object> list = new ArrayList<Object>();
            setParametersForInsert(list, dto);
            paraList.add(list);
        }

        int affectedRows = executeBatchUpdate(getSqlForInsert(), paraList);
        if (affectedRows != collection.size()) {
            throw new Exception("affectedRows(" + affectedRows + ") != collection.size(" + collection.size() + ")");
        }

        return affectedRows;
    }


    /**
     * 更新多条记录
     */
    public int batchUpdate(Collection collection) throws Exception {
        if (collection == null) return 0;

        List<List<Object>> paraList = new ArrayList<List<Object>>();
        for (Object obj : collection) {
            BaseDTO dto = (BaseDTO) obj;
            List<Object> list = new ArrayList<Object>();
            setParametersForUpdate(list, dto);
            paraList.add(list);
        }

        int affectedRows = executeBatchUpdate(getSqlForUpdate(), paraList);
        if (affectedRows != collection.size()) {
            throw new Exception("affectedRows(" + affectedRows + ") != collection.size(" + collection.size() + ")");
        }

        return affectedRows;
    }


    /**
     * 修改一条记录
     */
    public int update(BaseDTO dto) {
        List<Object> list = new ArrayList<Object>();
        setParametersForUpdate(list, dto);
        return executeUpdate(getSqlForUpdate(), list);
    }


    /**
     * 更新多条记录
     *
     * @return boolean
     * @throws java.sql.SQLException
     */
    public int update(Collection collection) {
        if (collection == null) return 0;

        ArrayList<List<Object>> paraList = new ArrayList<List<Object>>();
        for (Object obj : collection) {
            BaseDTO dto = (BaseDTO) obj;
            List<Object> list = new ArrayList<Object>();
            setParametersForUpdate(list, dto);
            paraList.add(list);
        }
        return executeBatchUpdate(getSqlForUpdate(), paraList);
    }

    /**
     * 删除一条记录
     *
     * @param key
     * @return boolean
     * @throws java.sql.SQLException
     */
    public int delete(Object key) {
        List<Object> list = new ArrayList<Object>();
        setParametersForDelete(list, key);
        String sqlForDelete = getSqlForDelete();
        logger.debug(sqlForDelete);
        int i_result = executeUpdate(sqlForDelete, list);
        logger.debug(i_result + " rows effected.");
        return i_result;
    }


    /**
     * 删除一条或多条记录
     */
    public int deleteByKeys(String keys) {
        String sqlForDelete = getSqlForDelete(keys);
        return executeUpdate(sqlForDelete);
    }


    /**
     * 清除表中所有内容
     *
     * @return
     * @throws java.sql.SQLException
     */
    public int deleteAll() {
        return executeUpdate("delete from " + tableName);
    }


    /**
     * 清除表中所有内容
     */
    public int truncate() {
        return executeUpdate("truncate " + tableName);
    }


    /**
     * 清除备份表中所有内容
     */
    public int truncateBackupTable() {
        return executeUpdate("truncate " + getBackupTableName(false));
    }


    /**
     * 清除备份表中所有内容
     */
    public int dropBackupTable() {
        return executeUpdate("drop table " + getBackupTableName(false));
    }


    /**
     * 创建备份表
     */
    public int backup() {
        return backup(false);
    }


    /**
     * 创建备份表
     */
    public int backup(boolean withTime) {
        return executeUpdate("create table " + getBackupTableName(withTime) + " as select * from " + getTableName());
    }


    /**
     * 重新备份（先删除再创建）备份表
     */
    public int reBackup() {
        if (existsTable(getBackupTableName(false))) {
            dropBackupTable();
        }
        return backup();
    }


    /**
     * 判断当前数据库中是否存在指定的表
     */
    public boolean existsBackupTable() {
        return existsTable(getBackupTableName(false));
    }


    /**
     * 判断当前数据库中是否存在指定的表
     */
    public boolean existsTable(String tableName) {
        final List<String> tableNameList = getStringList("show tables");
        for (String tableNameInDB : tableNameList) {
            if (tableNameInDB.equalsIgnoreCase(tableName)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 按条件删除表中的记录
     *
     * @return
     * @throws java.sql.SQLException
     */
    public int deleteByCondition(String condition, Object... params) {
        return executeUpdate("delete from " + getTableName() + " where " + condition, params);
    }


    /*****************************************************************************
     基础数据库操作
     *****************************************************************************/
    /**
     * 设置数据库连接
     * 那么不使用自己的数据库连接
     * 所以不需要在本实例中初始化、释放
     *
     * @param deliveredDBConnect
     */
    public void deliverDBConnect(DBConnect deliveredDBConnect) {
        this.dbConnect = deliveredDBConnect;

        // 不使用自己的连接
        this.isOwnDBConnect = false;
    }


    /**
     * 取得下一个ID
     *
     * @return
     * @throws java.sql.SQLException
     */
    public int getNextId() {
        int result = IdFactory.getNextId(getTableName(), getDbConnectConfig());
        ;
/*
//        if (isBatchMode) {
//            // 批量操作模式
//            if (nextId == UNINITIALIZED_NEXT_ID) {
//                // 未被初始化
//                // 初始化之
//                nextId = IdFactory.getNextId(getTableName(), getDbConnectConfig());
//            } else {
//                // 已经初始化
//            }
//            result = nextId;
//            nextId++;
//        } else {
//            // 不是批量操作
//            // 直接从数据库中取
//            result = IdFactory.getNextId(getTableName(), getDbConnectConfig());
//        }
*/
        return result;
    }


    /**
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery(String sql, List<Object> paramList) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, paramList);

            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeDBConnect();
        }
    }


    /**
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery(String sql, Object... params) {
        ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);

            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeDBConnect();
        }
    }


    /**
     * 执行插入、修改或删除语句
     */
    public int executeUpdate(String sql, List<Object> paramList) {
        try {
            int n_result = getDBConnect().update(sql, paramList);
            return n_result;
        } catch (SQLException e) {
            logger.error(e);
            logger.error("sql=" + sql);
            if (paramList != null) {
                for (int i = 0; i < paramList.size(); i++) {
                    logger.error(i + "\t[" + paramList.get(i) + "]");
                }
            }
            logger.error("----------------------------------------");
            throw new RuntimeException(e);
        } finally {
            closeDBConnect();
        }
    }


    /**
     * 执行插入、修改或删除语句
     *
     * @param sql
     * @param params
     * @return boolean
     * @throws java.sql.SQLException
     */
    public int executeUpdate(String sql, Object... params) {
        try {
            logger.debug(sql);
            int i_result = getDBConnect().update(sql, params);
            logger.debug(i_result + " rows effected.");
            return i_result;
        } catch (SQLException e) {
            logger.error(e);
            logger.error("sql=" + sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    logger.error(i + "\t[" + params[i] + "]");
                }
            }
            logger.error("----------------------------------------");
            throw new RuntimeException(e);
        } finally {
            closeDBConnect();
        }
    }

/*
    public int backupByCondition(String condition, Object... params) throws Exception {
        final int nrInsert = executeUpdate(
                "insert into " + getHistoryTableName() +
                        " select * from " + getTableName() +
                        " where " + condition,
                params);
        return nrInsert;
    }
*/


    /**
     * 批量执行插入、修改或删除语句
     *
     * @param sql
     * @return boolean
     * @throws java.sql.SQLException
     */
    public int executeBatchUpdate(String sql, List<List<Object>> paramList) {
        logger.debug("executeBatchUpdate: " + sql);
        // 得到数据库连接
        dbConnect = getDBConnect();
        boolean ori_autoCommit = dbConnect.getAutoCommit();
        try {
            // 关闭自动提交
            dbConnect.setAutoCommit(false);

            int nrAffectedRows = 0;

            // 准备SQL
            PreparedStatement ps = dbConnect.prepareStatement(sql);
            // 执行
            for (int i = 0; i < paramList.size(); i++) {
                if (i > 0 && i % 10000 == 0) {
                    logger.debug("update " + (i + 1) + "/" + paramList.size());
                }
                nrAffectedRows += dbConnect.update(ps, paramList.get(i));
            }

            // 提交
            if (ori_autoCommit) {
                dbConnect.commit();
            }

            return nrAffectedRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnect.setAutoCommit(ori_autoCommit);
            closeDBConnect();
        }
    }

    /**
     * 得到数据库连接
     */
    public DBConnect getDBConnect() {
        if (isBatchMode) {
            return dbConnect;
        }

        // 只有使用自有的连接，才创建连接
        // 非自有连接，由拥有连接权的BlBase传递进来
        if (isOwnDBConnect) {
            if (dbConnect == null) {
                dbConnect = new DBConnect(dbConnectConfig);
            }
        }

        return dbConnect;
    }

    /**
     * 关闭数据库连接
     */
    public void closeDBConnect() {
        if (isBatchMode) {
            return;
        }
        // 只有使用自有的连接，才释放连接
        // 非自有连接，由拥有连接权的BlBase释放
        if (isOwnDBConnect) {
            if (dbConnect != null) {
                if (!dbConnect.getAutoCommit()) {
                    // 如果不是自动提交，那么不用关闭，等提交后再关闭
                    return;
                }
                dbConnect.close();
                dbConnect = null;
            }
        }
    }


    /**
     * 开始事务
     */
    public void beginTransaction() {
        // 如果数据库连接是自己持有，那么关闭自动提交
        if (isOwnDBConnect) {
            if (dbConnect == null) {
                dbConnect = getDBConnect();
            }
            dbConnect.setAutoCommit(false);
        }
    }

    /**
     * 接收提供的数据库连接，开始事务
     */
    public void beginTransaction(DBConnect dbConnect) {
        this.deliverDBConnect(dbConnect);
    }

    /**
     * 结束批量操作
     */
    public void commitTransaction() throws Exception {
        // 如果数据库连接是自己持有，那么提交，然后开启自动提交
        if (isOwnDBConnect) {
            dbConnect.commit();
            dbConnect.setAutoCommit(true);
            dbConnect.close();
        }

        // 如果使用了idfactory，那么要保持idFactory
        if (nextId != UNINITIALIZED_NEXT_ID) {
            setNextId(nextId);
        }
    }


    /**
     * 保存ID
     *
     * @param nextId
     * @throws java.sql.SQLException
     */
    public void setNextId(int nextId) {
        IdFactory.setNextId(getTableName(), nextId);
    }


    /**
     * 回滚事务
     */
    public void rollbackTransaction() throws Exception {
        // 如果数据库连接是自己持有，那么提交，然后开启自动提交
        if (isOwnDBConnect) {
            dbConnect.rollback();
            // 默认自动提交
            dbConnect.setAutoCommit(true);
            dbConnect.close();
        }

        // 如果使用了idfactory，那么要保持idFactory
        nextId = UNINITIALIZED_NEXT_ID;
    }

    /**
     * 开始批量操作
     */
    public void beginBatch() {
        isBatchMode = true;
    }


    /**
     * 接收提供的数据库连接，开始批量操作
     */
    public void beginBatch(DBConnect dbConnect) {
        this.deliverDBConnect(dbConnect);
        beginBatch();
    }

    /**
     * 结束批量操作
     */
    public void commitBatch() throws Exception {
        if (!isBatchMode) {
            throw new Exception("Method \"endBatch()\" is not allowed being invoked before method \"beginBatch()\"!");
        }

        // 如果使用了idfactory，那么要保持idFactory
        if (nextId != UNINITIALIZED_NEXT_ID) {
            setNextId(nextId);
        }

        isBatchMode = false;
    }

    /**
     * 回滚批量操作
     */
    public void rollbackBatch() throws Exception {
        if (!isBatchMode) {
            throw new Exception("Method \"rollbackBatch()\" is not allowed being invoked before method \"beginBatch()\"!");
        }

        // 如果数据库连接是自己持有，那么提交，然后开启自动提交
        if (isOwnDBConnect) {
            dbConnect.rollback();
            // 默认自动提交
            dbConnect.setAutoCommit(true);
            dbConnect.close();
        }

        // 如果使用了idfactory，那么要保持idFactory
        nextId = UNINITIALIZED_NEXT_ID;

        // 默认非批处理模式
        isBatchMode = false;
    }


    protected char getChar(ResultSet res, String fieldName) {
        final String s;
        try {
            s = res.getString(fieldName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (s != null && s.length() > 0) {
            return s.charAt(0);
        } else {
            return ' ';
        }
    }


    public Set<String> getFieldSetSet(String field) {
        ResultSet res = null;

        try {
            // 取记录
            StringBuffer sqlForGetList = new StringBuffer();

            // 构造查询语句
            sqlForGetList.append("SELECT");

            // 查询字段
            sqlForGetList.append(" ");
            sqlForGetList.append(field);
            sqlForGetList.append("\nFROM ").append(getTableName());

            res = getDBConnect().executeQuery(sqlForGetList.toString());
            Set<String> set = new HashSet<String>();
            while (res.next()) {
                set.add(res.getString(1));
            }

            return set;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                closeDBConnect();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }


    public String getDbConnectConfig() {
        return dbConnectConfig;
    }

    public void setDbConnectConfig(String dbConnectConfig) {
        this.dbConnectConfig = dbConnectConfig;
    }


    /**
     * 从varbinary读取字符串
     * @param res
     * @param columnName
     * @return
     * @throws SQLException
     */
    protected String getStringFromVarbinary(ResultSet res, String columnName) throws SQLException {
        byte[] bytes = res.getBytes(columnName);
        if(bytes == null) {
            return null;
        } else {
            return new String(bytes);
        }
    }


    /**
     * 设置表名
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}