package org.ailab.wimfra.database;

import org.ailab.wimfra.util.P;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2010-10-24
 * Time: 13:19:51
 * Desc: 表示数据库中的表
 */
public class Table {
    // 表名
    public String tableName;
    // 列数
    public int colNum;
    // 列名
    public String[] colNames;
    // 行的列表
    public ArrayList<Row> rowList;
    // 行数
    public int rowNum;
    // 列名到列序号的映射
    public HashMap<String, Integer> colNameToColIdxMap;

    /**
     * 实例化
     * @param tableName
     * @return
     */
    public static Table instantiate(String tableName) {
        Table table = new Table();
        table.tableName = tableName;

        return table;
    }


    /**
     * 实例化
     * @param tableName
     * @param colNames
     * @param rowList
     * @return
     */
    public static Table instantiate(String tableName, String[] colNames, ArrayList<Row> rowList) {
        Table table = new Table();
        table.tableName = tableName;
        table.setColNames(colNames);
        table.setRowList(rowList);

        return table;
    }


    /**
     * 实例化，并读入表
     * @param tableName
     * @return
     * @throws java.sql.SQLException
     */
    public static Table read(String tableName) throws SQLException {
        Table table = new Table();
        table.tableName = tableName;
        table.read();

        return table;
    }

    /**
     * 设置行的列表
     * @param rowList
     */
    public void setRowList(ArrayList<Row> rowList) {
        this.rowList = rowList;

        if (rowList != null) {
            this.rowNum = rowList.size();
        }
    }

    /**
     * 设置列名
     * @param colNames
     */
    public void setColNames(String[] colNames) {
        this.colNames = colNames;

        if (colNames != null) {
            this.colNum = colNames.length;
            initColNameToColIdxMap();
        }
    }

    /**
     * 测试表是否为空
     * @return
     */
    public boolean isEmpty() {
        return rowNum == 0;
    }

    /**
     * 得到插入语句
     * @return
     */
    public String getInsertSql() {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ").append(tableName).append(" (");
        sb.append(colNames[0]);
        for (int i = 1; i < colNum; i++) {
            sb.append(", ").append(colNames[i]);
        }
        sb.append(") VALUES(");
        sb.append('?');
        for (int i = 1; i < colNum; i++) {
            sb.append(", ?");
        }
        sb.append(")");

        return sb.toString();
    }


    /**
     * 从数据库中读取内容
     */
    public void read() throws SQLException {
        P.pstart("read");

        String sql = "SELECT * FROM " + tableName;
        System.out.println("SQL: " + sql);

        ArrayList<Row> rowList = new ArrayList<Row>();

        java.sql.ResultSet res = null;
        DBConnect dbc = null;

        try {
            dbc = new DBConnect();
            res = dbc.executeQuery(sql);

            setColNames(DBUtil.getColumnNames(res));

            while (res.next()) {
                Row row = createRow();
                for (int i = 0; i < colNum; i++) {
                    row.colValues[i] = res.getString(i + 1);
                }
                rowList.add(row);
            }

            System.out.println(rowList.size() + " rows read.");

            setRowList(rowList);
        } catch (SQLException e) {
            throw e;
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

        P.pfinish();
    }

    /**
     * 插入数据库
     */
    public void insert() throws SQLException {
        P.pstart("insert");

        if (isEmpty()) {
            System.out.println("Empty table.");
            P.pfinish();
            return;
        }

        DBConnect dbc = new DBConnect();
        dbc.setAutoCommit(false);

        final String sql = getInsertSql();
        System.out.println("SQL: " + sql);
        PreparedStatement preStatment = dbc.prepareStatement(sql);

        for (Row row : rowList) {
            for (int i = 0; i < colNum; i++) {
                //设置参数值
                preStatment.setString(i + 1, row.colValues[i]);
            }
            preStatment.executeUpdate();
        }

        System.out.println("Commit ...");
        dbc.commit();
        System.out.println(rowNum + " records committed.");

        P.pfinish();
    }

    /**
     * 初始化列名到列序号的映射表
     */
    private void initColNameToColIdxMap() {
        colNameToColIdxMap = new HashMap<String, Integer>();
        for (int i = 0; i < colNum; i++) {
            colNameToColIdxMap.put(colNames[i], i);
        }
    }

    /**
     * 创建行
     * @return
     */
    public Row createRow() {
        return new Row(colNum);
    }

    /**
     * 得到列序号
     * @param colName
     * @return
     * @throws Exception
     */
    public int getColIdx(String colName) throws Exception {
        final Integer integer = colNameToColIdxMap.get(colName);
        if (integer == null) {
            throw new Exception("Can not find column: " + colName);
        }

        return integer;
    }
}
