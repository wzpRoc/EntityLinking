package org.ailab.wimfra.frontend.graph;

import org.ailab.wimfra.database.DBConnect;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.Stopwatch;

import java.sql.SQLException;

/**
 * User: Lu Tingming
 * Date: 2012-6-23
 * Time: 21:04:58
 * Desc:
 */
public class DBFetcher {
    public Logger logger = Logger.getLogger(DBFetcher.class);
    public String dbConfigName;


    /**
     * 得到查询结果集的行列表
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public DBRowList getDBRowList(String sql, int numSize, Object... params) throws Exception {
        java.sql.ResultSet res = null;
        DBConnect dbc = null;

        logger.debug("dbConfigName: " + dbConfigName);
        logger.debug(sql);
        Stopwatch stopwatch = new Stopwatch();

        try {
            dbc = new DBConnect(dbConfigName);
            if (params.length == 0) {
                res = dbc.executeQuery(sql);
            } else if (params.length == 1) {
                res = dbc.executeQuery(sql, params[0]);
            } else {
                res = dbc.executeQuery(sql, params);
            }

            DBRowList dbRowList = DBRowList.create(numSize);
            while (res.next()) {
                dbRowList.addRow(res);
            }

            logger.debug(dbRowList.size() + " rows read.");
            stopwatch.stop();
            logger.debug("Time cost: " + stopwatch.getSeconds() + " seconds");

            // P.pfinish();
            return dbRowList;
        } catch (Exception e) {
            // P.pfinish();
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
    }

    public String getDbConfigName() {
        return dbConfigName;
    }

    public void setDbConfigName(String dbConfigName) {
        this.dbConfigName = dbConfigName;
    }
}
