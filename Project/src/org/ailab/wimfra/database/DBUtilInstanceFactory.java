package org.ailab.wimfra.database;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 2010-11-12 12:42:59
 * Desc: 数据库操作工具工厂
 */
public class DBUtilInstanceFactory {
    private static Map<String, DBUtilInstance> map = new HashMap<String, DBUtilInstance>();

    public static DBUtilInstance getInstance(String dbConfigName) {
        DBUtilInstance instance = map.get(dbConfigName);
        if(instance ==null) {
            instance = new DBUtilInstance(dbConfigName);
            map.put(dbConfigName, instance);
        }

        return instance;
    }
}