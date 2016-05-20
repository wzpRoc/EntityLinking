package org.ailab.wimfra.core.idFactory;

import org.ailab.wimfra.database.DBUtilInstance;
import org.ailab.wimfra.database.DBUtilInstanceFactory;

import java.sql.SQLException;

/**
 * User: Lu Tingming
 * Date: 2011-6-6
 * Time: 12:22:53
 * Desc: ID产生器
 */
public class IdFactory {
    private static DBUtilInstance dbUtilInstance = new DBUtilInstance();

    /**
     * 得到下一个ID
     *
     * @param name 名称
     * @return 下一个ID
     * @throws java.sql.SQLException
     */
    public synchronized static int getNextId(String name) throws SQLException {
        return getNextId(name, null);
    }

    public synchronized static int getNextId(String name, String dbConfigName) {
        // 查询之前的最大ID
        int maxId = getDBUtilInstance(dbConfigName).getInt("SELECT maxId FROM idFactory WHERE name = ?", name);

        // 得到之后的最大ID
        int nextId;
        if (maxId != Integer.MIN_VALUE) {
            // 查询成功
            nextId = maxId + 1;
            // 更新
            getDBUtilInstance(dbConfigName).executeUpdate("UPDATE idFactory SET maxId = ? WHERE name = ?", nextId, name);
        } else {
            // 没有查询到，那么增加条目
            nextId = 1;
            getDBUtilInstance(dbConfigName).executeUpdate("INSERT INTO idFactory(name, maxId) VALUES (?, ?)", name, nextId);
        }

        return nextId;
    }

    private static DBUtilInstance getDBUtilInstance(String dbConfigName) {
        DBUtilInstance dbUtilInstance;
        if(dbConfigName == null) {
            dbUtilInstance = IdFactory.dbUtilInstance;
        } else {
            dbUtilInstance = DBUtilInstanceFactory.getInstance(dbConfigName);
        }

        return dbUtilInstance;
    }

    /**
     * 设置下一个ID
     */
    public synchronized static void setNextId(String name, int nextId) {
        setNextId(name, nextId, null);
    }

    public synchronized static void setNextId(String name, int nextId, String dbConfigName) {
        // 更新
        getDBUtilInstance(dbConfigName).executeUpdate("UPDATE idFactory SET maxId = ? WHERE name = ?", nextId, name);
    }

    /**
     * for wzp.newsML.test
     */
    public static void main(String[] args) throws SQLException {
        System.out.println(getNextId("knowledge"));
    }
}