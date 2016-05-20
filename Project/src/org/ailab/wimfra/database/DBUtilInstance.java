package org.ailab.wimfra.database;

import org.ailab.wimfra.util.*;
import org.ailab.wimfra.util.map.MultiValueMap;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


/**
 * User: Lu Tingming
 * Date: 2010-11-12 12:42:59
 * Desc: 数据库操作工具
 */
public class DBUtilInstance {
    protected boolean isDebug = true;
    protected Logger logger = Logger.getLogger(DBUtilInstance.class);
    protected String dbConnectConfigName;
    protected boolean batchMode = false;
    public DBConnect dbc;


    public DBUtilInstance() {
    }

    public DBUtilInstance(String dbConnectConfigName) {
        this.dbConnectConfigName = dbConnectConfigName;
    }

    private DBConnect getDBConnect() {
        if (dbc == null) {
            dbc = new DBConnect(dbConnectConfigName);
        }
        return dbc;
    }

    public void close() {
        if (batchMode) {
            return;
        }

        closeImmediately();
    }

    protected void closeImmediately() {
        if (dbc != null) {
            dbc.close();
        }
        dbc = null;
    }

    public void beginBatch() throws SQLException {
        batchMode = true;
        dbc = getDBConnect();
        dbc.setAutoCommit(false);
    }

    public void commitBatch() throws SQLException {
        batchMode = false;
        dbc.commit();
        closeImmediately();
    }

    public void rollbackBatch() throws SQLException {
        batchMode = false;
        dbc.rollback();
        closeImmediately();
    }

    /**
     * 得到列表，每一行记录为列表的一个元素，每条记录的多个字段记录为字符串数组
     */
    public List<BiString> getBiStringList(String sql, Object... params) throws SQLException {
        java.sql.ResultSet res = null;

        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            // construct data list
            ArrayList<BiString> dataList = new ArrayList<BiString>();
            int cnt = 0;
            while (res.next()) {
                cnt++;
                if (cnt % 10000 == 0) {
                    System.out.println(cnt + " rows loaded.");
                }
                BiString record = new BiString(res.getString(1), res.getString(2));
                dataList.add(record);
            }

            return dataList;
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
            close();
        }
    }


    /**
     * 得到列表，每一行记录为列表的一个元素，每条记录的多个字段记录为字符串数组
     */
    public List<KeyValue> getKeyValueList(String tableName, String keyFieldName, String valueFieldName) throws SQLException {
        return getKeyValueList("SELECT " + keyFieldName + ", " + valueFieldName + " FROM " + tableName);
    }


    /**
     * 得到列表，每一行记录为列表的一个元素，每条记录的多个字段记录为字符串数组
     */
    public List<KeyValue> getKeyValueList(String sql, Object... params) {
        java.sql.ResultSet res = null;

        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            // construct data list
            List<KeyValue> keyValueList = new ArrayList<KeyValue>();
            int cnt = 0;
            while (res.next()) {
                cnt++;
                if (cnt % 10000 == 0) {
                    System.out.println(cnt + " rows loaded.");
                }
                KeyValue keyValue = new KeyValue(res.getString(1), res.getString(2));
                keyValueList.add(keyValue);
            }

            return keyValueList;
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
            close();
        }
    }


    /**
     * 得到列表，每一行记录为列表的一个元素，每条记录的多个字段记录为字符串数组
     */
    public List<BiInteger> getBiIntegerList(String sql, Object... params) throws SQLException {
        java.sql.ResultSet res = null;

        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            // construct data list
            ArrayList<BiInteger> dataList = new ArrayList<BiInteger>();
            int cnt = 0;
            while (res.next()) {
                cnt++;
                if (cnt % 10000 == 0) {
                    System.out.println(cnt + " rows loaded.");
                }
                BiInteger record = new BiInteger(res.getInt(1), res.getInt(2));
                dataList.add(record);
            }

            return dataList;
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
            close();
        }
    }


    /**
     * 得到列表，每一行记录为列表的一个元素，每条记录的多个字段记录为字符串数组
     */
    public List<Integer> getIntegerList(String sql, Object... params) {
        java.sql.ResultSet res = null;

        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            // construct data list
            ArrayList<Integer> dataList = new ArrayList<Integer>();
            int cnt = 0;
            while (res.next()) {
                cnt++;
                if (cnt % 10000 == 0) {
                    System.out.println(cnt + " rows loaded.");
                }
                dataList.add(res.getInt(1));
            }

            return dataList;
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
            close();
        }
    }


    /**
     * 得到列表，每一行记录为列表的一个元素，每条记录的多个字段记录为字符串数组
     */
    public Set<Integer> getIntegerSet(String sql, Object... params) {
        java.sql.ResultSet res = null;

        try {
            logger.debug(sql);
            res = getDBConnect().executeQuery(sql, params);

            // construct data list
            Set<Integer> set = new HashSet<Integer>();
            int cnt = 0;
            while (res.next()) {
                cnt++;
                if (cnt % 10000 == 0) {
                    System.out.println(cnt + " rows loaded.");
                }
                set.add(res.getInt(1));
            }

            return set;
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
            close();
        }
    }


    public boolean dropTable(String tableName) {
        executeUpdate("drop table " + tableName);
        return true;
    }

    public boolean truncateTable(String tableName) {
        executeUpdate("truncate table " + tableName);
        return true;
    }


    /**
     * 执行DML语句
     */
    public void executeUpdate(String sql, Object... param) {
        logger.debug("executeUpdate:\n" + sql);

        PreparedStatement preStatment = null;

        try {
            preStatment = getDBConnect().prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                DBConnect.setParameter(preStatment, i + 1, param[i]);
            }
            preStatment.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preStatment != null) {
                try {
                    preStatment.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                preStatment = null;
            }
            close();
        }

    }


    /**
     * 批量修改
     * sql中包含一个参数
     */
    public int batchExecuteWithOneValueCollection(String sql, Collection valueCollection) {
        logger.debug("batchExecuteWithOneValueCollection ...");
        logger.debug(sql);

        try {
            getDBConnect().setAutoCommit(false);

            int nrAffectedRows = 0;

            PreparedStatement preStatement = getDBConnect().prepareStatement(sql);

            int size = valueCollection.size();
            final Iterator iterator = valueCollection.iterator();
            for (int i = 0; i < size; i++) {
                Object obj = iterator.next();
                if (obj == null) {
                    break;
                }

                if (i % 10000 == 0) {
                    logger.debug("executing " + (i + 1) + "/" + size);
                }
                DBConnect.setParameter(preStatement, 1, obj);
                nrAffectedRows += preStatement.executeUpdate();
            }

            getDBConnect().commit();
            close();
            logger.debug(size + " statements executed.");
            logger.debug(nrAffectedRows + " rows affected.");

            return nrAffectedRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 批量插入对象列表
     */
    public void insertObjectList(String tableName, List objectList) throws Exception {
        logger.debug("insertObjectList ...");

        if (objectList == null || objectList.size() == 0) {
            return;
        }

        dbc = getDBConnect();
        dbc.setAutoCommit(false);

        Object firstObj = objectList.get(0);
        Field[] objectFields = ClassUtil.getSimpleFields(firstObj.getClass());

        Set<String> tableFields = DBUtil.getTableFields(tableName);
        objectFields = DBUtil.filter(objectFields, tableFields);

        String[] fieldNames = ClassUtil.getFieldNames(objectFields);

        final String s = DBUtil.getInsertSql(tableName, fieldNames);
        logger.debug(s);
        PreparedStatement preStatement = dbc.prepareStatement(s);

        final int fieldNum = fieldNames.length;

        Object obj = null;
        int i_obj = 0;
        try {
            for (i_obj = 0; i_obj < objectList.size(); i_obj++) {
                obj = objectList.get(i_obj);
                for (int i = 0; i < fieldNum; i++) {
                    //设置参数值
                    DBConnect.setParameter(preStatement, i + 1, objectFields[i].get(obj));
                }
                preStatement.executeUpdate();
            }

            logger.debug("Commit ...");
            dbc.commit();
            logger.debug(objectList.size() + " records committed.");
        } catch (Exception e) {
            if (obj != null) {
                logger.debug("object:");
                for (int i = 0; i < fieldNum; i++) {
                    logger.debug(objectFields[i].getName() + "\t" + objectFields[i].get(obj));
                }
            }
            throw new Exception("Exception at " + i_obj + "\n" + e.toString());
        } finally {
            if (preStatement != null) {
                try {
                    preStatement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                preStatement = null;
            }
            close();
        }
    }


    /**
     * 按照指定的列名，修改对象
     */
    public void updateObjectByFieldName(String tableName, Object obj, String keyFieldName, String... valueFieldNames) throws Exception {
        PreparedStatement preStatement = null;
        try {
            logger.debug("updateObjectListByFieldName ...");

            StringBuffer sb = new StringBuffer();
            sb.append("UPDATE ").append(tableName).append(" SET ");

            int nValue = valueFieldNames.length;
            for (int i = 0; i < nValue - 1; i++) {
                sb.append(valueFieldNames[i]).append("=?, ");
            }
            sb.append(valueFieldNames[nValue - 1]).append("=? ");

            sb.append(" WHERE ").append(keyFieldName).append("=?");
            final String sql = sb.toString();
            logger.debug(sql);

            preStatement = getDBConnect().prepareStatement(sql);

            //设置参数值
            Class clazz = obj.getClass();
            Field keyField = clazz.getField(keyFieldName);
            Field[] valueFields = new Field[valueFieldNames.length];
            for (int i = 0; i < valueFieldNames.length; i++) {
                String valueFieldName = valueFieldNames[i];
                valueFields[i] = clazz.getField(valueFieldName);
            }
            for (int i_para = 0; i_para < valueFields.length; i_para++) {
                DBConnect.setParameter(preStatement, i_para + 1, valueFields[i_para].get(obj));
            }
            DBConnect.setParameter(preStatement, valueFields.length + 1, keyField.get(obj));
            preStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (preStatement != null) {
                try {
                    preStatement.close();
                    preStatement = null;
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            close();
        }
    }


    /**
     * 按照指定的列名，批量修改对象列表
     */
    public void updateObjectListByFieldName(String tableName, List objList, String keyFieldName, String... valueFieldNames) throws Exception {
        logger.debug("updateObjectListByFieldName ...");

        if (objList == null || objList.size() == 0) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ").append(tableName).append(" SET ");

        int nValue = valueFieldNames.length;
        for (int i = 0; i < nValue - 1; i++) {
            sb.append(valueFieldNames[i]).append("=?, ");
        }
        sb.append(valueFieldNames[nValue - 1]).append("=? ");

        sb.append(" WHERE ").append(keyFieldName).append("=?");
        final String sql = sb.toString();
        logger.debug(sql);

        beginBatch();

        PreparedStatement preStatement = dbc.prepareStatement(sql);

        //设置参数值
        Class clazz = objList.get(0).getClass();
        Field keyField = clazz.getField(keyFieldName);
        Field[] valueFields = new Field[valueFieldNames.length];
        for (int i = 0; i < valueFieldNames.length; i++) {
            String valueFieldName = valueFieldNames[i];
            valueFields[i] = clazz.getField(valueFieldName);
        }
        int size = objList.size();
        for (int i = 0; i < size; i++) {
            if ((i + 1) % 10000 == 0) {
                logger.debug("processing " + (i + 1) + "/" + size);
            }
            Object obj = objList.get(i);
            for (int i_para = 0; i_para < valueFields.length; i_para++) {
                DBConnect.setParameter(preStatement, i_para + 1, valueFields[i_para].get(obj));
            }
            DBConnect.setParameter(preStatement, valueFields.length + 1, keyField.get(obj));
            preStatement.executeUpdate();
        }

        commitBatch();
        logger.debug(size + " rows committed.");
    }


    /**
     * 得到列表，每一行记录为列表的一个元素，每条记录的多个字段记录为字符串数组
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public List<String[]> getStringsList(String sql, Object... params) {
        if (StringUtil.notEmpty(dbConnectConfigName)) {
            logger.debug("dbConnectConfigName=" + dbConnectConfigName);
        }
        logger.debug("getStringsList");
        logger.debug(sql);
        java.sql.ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);

            int columnNum = res.getMetaData().getColumnCount();

            // construct data list
            ArrayList<String[]> dataList = new ArrayList<String[]>();
            int cnt = 0;
            while (res.next()) {
                cnt++;
                if (cnt % 10000 == 0) {
                    logger.debug(cnt + " rows loaded.");
                }
                String[] record = new String[columnNum];
                for (int i = 0; i < columnNum; i++) {
                    record[i] = res.getString(i + 1);
                }
                dataList.add(record);
            }

            return dataList;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
    }


    /**
     * 得到列表
     */
    public List<String> getStringList(String sql, Object... params) {
        if (StringUtil.notEmpty(dbConnectConfigName)) {
            logger.debug("dbConnectConfigName=" + dbConnectConfigName);
        }
        logger.debug("getStringList");
        logger.debug(sql);
        java.sql.ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);

            int columnNum = res.getMetaData().getColumnCount();

            // construct data list
            ArrayList<String> dataList = new ArrayList<String>();
            int cnt = 0;
            while (res.next()) {
                cnt++;
                if (cnt % 10000 == 0) {
                    logger.debug(cnt + " rows loaded.");
                }
                dataList.add(res.getString(1));
            }

            return dataList;
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
    }


    /**
     * 得到列表
     * 根据sql中的列名，填充到实例的相应字段
     * 如果找不到字段，那么异常
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList getObjectList(String sql, Class clazz, Object... params) throws Exception {
        logger.debug(sql);

        java.sql.ResultSet res = null;

        try {
            if (params.length == 0) {
                res = getDBConnect().executeQuery(sql);
            } else if (params.length == 1) {
                res = getDBConnect().executeQuery(sql, params[0]);
            } else {
                res = getDBConnect().executeQuery(sql, params);
            }

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
            int cnt = 0;
            while (res.next()) {
                if (cnt % 10000 == 0) {
                    logger.debug(cnt + " loaded");
                }
                cnt++;
                Object obj = clazz.newInstance();
                for (int i = 0; i < colNum; i++) {
                    Field field = fieldsOrderedByColumn[i];
                    if (field == null) continue;
                    final String fieldType = field.getType().getName();
                    if (fieldType.equals("java.lang.String")) {
                        field.set(obj, res.getString(i + 1));
                    } else if (fieldType.equals("int")) {
                        field.set(obj, res.getInt(i + 1));
                    } else if (fieldType.equals("double")) {
                        field.set(obj, res.getDouble(i + 1));
                    } else if (fieldType.equals("boolean")) {
                        field.set(obj, res.getBoolean(i + 1));
                    } else if (fieldType.equals("char")) {
                        final String s = res.getString(i + 1);
                        final char c;
                        if (s.length() == 0) {
                            c = ' ';
                        } else {
                            c = s.charAt(0);
                        }
                        field.set(obj, c);
                    } else {
                        field.set(obj, res.getObject(i + 1));
                    }
                }
                list.add(obj);
            }

            logger.debug(list.size() + " rows read.");

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
    }


    /**
     * 得到整数
     * 如果返回Integer.MIN_VALUE，表示没有查询到结果
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public int getInt(String sql, Object... params) {
        java.sql.ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql, params);

            if (res.next()) {
                return res.getInt(1);
            }

            return Integer.MIN_VALUE;
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
            close();
        }
    }


    /**
     * 得到Map，其中查询的第一个字段为键，第二个字段为值
     */
    public Map<String, String> getStringStringMap(String sql) throws SQLException {
        logger.debug(sql);
        java.sql.ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql);

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
            close();
        }
    }


    /**
     * 得到Map，其中查询的第一个字段为键，第二个字段为值
     */
    public Map<String, Integer> getStringIntegerMap(String sql) {
        logger.debug(sql);
        java.sql.ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql);

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
            close();
        }
    }


    /**
     * 得到Map，其中查询的第一个字段为键，第二个字段为值
     */
    public Map<String, Integer> getStringStringIntegerMap(String sql, String stringConcat) {
        logger.debug(sql);
        java.sql.ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql);

            HashMap<String, Integer> map = new HashMap<String, Integer>();
            while (res.next()) {
                map.put(res.getString(1) + stringConcat + res.getString(2), res.getInt(3));
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
            close();
        }
    }


    /**
     * 得到Map，其中查询的第一个字段为键，第二个字段为值
     */
    public Map<Integer, Integer> getIntegerIntegerMap(String sql) {
        logger.debug(sql);
        java.sql.ResultSet res = null;

        try {
            res = getDBConnect().executeQuery(sql);

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
            close();
        }
    }


    public int update(String tableName, String keyFieldName, String valueFieldName, IValueProcessor valueProcessor) throws Exception {
        List<KeyValue> keyValueList = getKeyValueList(tableName, keyFieldName, valueFieldName);

        for (KeyValue keyValue : keyValueList) {
            keyValue.setValue(valueProcessor.process(keyValue.getValue()));
        }

        return updateByKeyValueList(tableName, keyFieldName, valueFieldName, keyValueList);
    }


    /**
     * 按照“键”查找记录，更新“值”
     */
    public int updateByKeyValueList(String tableName, String keyFieldName, String valueFieldName, List<KeyValue> keyValueList) throws Exception {
        logger.debug("updateByKeyValueList ...");

        DBConnect dbc = getDBConnect();
        dbc.setAutoCommit(false);

        StringBuffer sb = new StringBuffer();
        sb
                .append("UPDATE ").append(tableName).append(" SET ")
                .append(valueFieldName).append("=? WHERE ")
                .append(keyFieldName).append("=?");
        final String sql = sb.toString();
        logger.debug(sql);

        PreparedStatement preStatement = dbc.prepareStatement(sql);

        //设置参数值
        final int size = keyValueList.size();
        int affectedRowsCount = 0;
        for (int i = 0; i < size; i++) {
            if ((i + 1) % 10000 == 0) {
                logger.debug("processing " + (i + 1) + "/" + size);
            }
            KeyValue keyValue = keyValueList.get(i);
            DBConnect.setParameter(preStatement, 1, keyValue.getValue());
            DBConnect.setParameter(preStatement, 2, keyValue.getKey());
            affectedRowsCount += preStatement.executeUpdate();
        }

        dbc.commit();
        logger.debug(size + " rows committed.");

        return affectedRowsCount;
    }


    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getDbConnectConfigName() {
        return dbConnectConfigName;
    }

    public void setDbConnectConfigName(String dbConnectConfigName) {
        this.dbConnectConfigName = dbConnectConfigName;
    }

}