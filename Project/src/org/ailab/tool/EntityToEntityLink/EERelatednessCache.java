package org.ailab.tool.EntityToEntityLink;

import org.ailab.wimfra.database.DBConnect;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: lutingming
 * Date: 16-1-2
 * Time: 下午8:25
 * Desc: Entity-Entity Semantic Relatedness
 */
public class EERelatednessCache {
    private static EERelatednessCache instance;

    private EERelatednessMap eeRelatednessMap;
    private Map<Integer, Set<Integer>> emptySet;

    Logger logger = Logger.getLogger(EERelatednessCache.class);

    public static EERelatednessCache getInstance() {
        if(instance==null) {
            instance = new EERelatednessCache();
        }

        return instance;
    }

    public EERelatednessCache() {
        eeRelatednessMap = new EERelatednessMap();
    }

    public boolean contains(int id0, int id1) {
        return eeRelatednessMap.contains(id0, id1);
    }


    public double get(int id0, int id1) {
        double resultFromMap = eeRelatednessMap.get(id0, id1);
        if(Double.isNaN(resultFromMap)) {
            //emptySet.get(id0)
        }
        return resultFromMap;
    }
}
