package org.ailab.wimfra.frontend.graph;

import org.ailab.wimfra.util.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 21:03:13
 * Desc: 数据库查询结果集以数据行的列表表示
 */
public class DBRowList extends ArrayList<DBRow> {
    /**
     * 包含的数字数量
     */
    public int numSize;


    /**
     * 创建一个数据行的列表
     * @param numSize
     * @return
     */
    public static DBRowList create(int numSize){
        DBRowList dbRowList = new DBRowList();
        dbRowList.numSize = numSize;

        return dbRowList;
    }


    /**
     * 加入一个数据行
     * @param res
     * @throws java.sql.SQLException
     */
    public void addRow(java.sql.ResultSet res) throws SQLException {
        DBRow dbRow = new DBRow(numSize);
        add(dbRow);

        dbRow.id = res.getString("id");
        dbRow.label = res.getString("label");
        dbRow.dateBegin = res.getString("dateBegin");
        dbRow.dateEnd = res.getString("dateEnd");
        for(int i = 0; i<numSize; i++) {
            dbRow.numList.add(res.getDouble("num"+i));
            String temp = res.getString("toolTips" + i);
            if(StringUtil.notEmpty(temp)) {
                temp = temp.replace("#PIAN#", "篇");
            }
            dbRow.addToolTips(temp);
        }
    }


    /**
     * 加入一个数据行
     * @throws java.sql.SQLException
     */
    public void addRow(DBRow dbRow) throws SQLException {
        add(dbRow);
    }
}
