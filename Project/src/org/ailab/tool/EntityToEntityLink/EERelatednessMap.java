package org.ailab.tool.EntityToEntityLink;

import org.ailab.wimfra.database.DBConnect;
import org.ailab.wimfra.database.DBUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: lutingming
 * Date: 16-1-2
 * Time: 下午8:25
 * Desc: Entity-Entity Semantic Relatedness
 */
public class EERelatednessMap extends HashMap<Integer, Map<Integer, Double>> {
    private static EERelatednessMap instance;

    Logger logger = Logger.getLogger(EERelatednessMap.class);
    int count=0;

    public static EERelatednessMap getInstance() {
        if(instance==null) {
            instance = new EERelatednessMap();
//            instance.load();
            instance.loadFromFile();
        }

        return instance;
    }


    /**
     * 从数据库表载入数据构建Map
     */
    public void load() {
        logger.info("load");

        ResultSet res = null;
        DBConnect dbc = null;

        String sql = "select entityId0, entityId1, relatedness from EERelatedness";
        logger.debug(sql);

        int count = 0;
        try {
            dbc = new DBConnect();
            res = dbc.executeQuery(sql);

            while (res.next()) {
                if(count % 100000 == 0) {
                    logger.debug(count+ " loaded");
                }
                put(res.getInt(1), res.getInt(2), res.getDouble(3));
                count++;
            }

            logger.debug(size() + " rows loaded");
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
     * 从数据库表载入数据构建Map
     */
    public void loadFromFile() {
        logger.info("loadFromFile");

        int count = 0;
        File file = new File("D:\\EERelatedness.csv");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String strLine;
            while (true) {
                strLine = reader.readLine();
                if (strLine == null) break;
                String[] as = strLine.split("\t");
                put(Integer.parseInt(as[0]),
                        Integer.parseInt(as[1]),
                        Double.parseDouble(as[2])
                        );

                count++;
                if(count % 100000 == 0) {
                    long occupiedMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
                    occupiedMemory = occupiedMemory / (1024*1024);
                    logger.debug(count+ " loaded, "+occupiedMemory+"MB");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * 保存当前对象到数据库
     * @throws SQLException
     */
    public void save() throws SQLException {
        DBConnect dbc = new DBConnect();
        dbc.setAutoCommit(false);

        String sql = "insert into EERelatedness(entityId0, entityId1, relatedness) values(?, ?, ?)";

        PreparedStatement preStatement = dbc.prepareStatement(sql);

        int count = 0;
        //设置参数值
        for (Map.Entry<Integer, Map<Integer, Double>> entry : entrySet()) {
            count++;
            System.out.println("processing " + count + "/" + size());

            int entityId0 = entry.getKey();
            Map<Integer, Double> subMap = entry.getValue();
            for(Map.Entry<Integer, Double> subEntry : subMap.entrySet()) {
                int entityId1 = subEntry.getKey();
                double relatedness = subEntry.getValue();
                DBConnect.setParameter(preStatement, 1, entityId0);
                DBConnect.setParameter(preStatement, 2, entityId1);
                DBConnect.setParameter(preStatement, 3, relatedness);
                preStatement.executeUpdate();
            }
        }

        dbc.commit();
        System.out.println(count + " rows committed.");
    }


    public void put(int id0, int id1, double semanticRelatedness) {
        if(id0>id1) {
            int temp = id0;
            id0 = id1;
            id1 = temp;
        }
        Map<Integer, Double> subMap = get(id0);
        if(subMap == null) {
            subMap = new HashMap<Integer, Double>();
            put(id0, subMap);
        }
        subMap.put(id1, semanticRelatedness);
        count++;
        if(count % 100000 == 0) {
            System.out.println(count+" relatedness("+id0+", "+id1+")="+semanticRelatedness);
        }
    }


    public boolean contains(int id0, int id1) {
        if(id0>id1) {
            int temp = id0;
            id0 = id1;
            id1 = temp;
        }
        Map<Integer, Double> subMap = get(id0);
        if(subMap == null) {
            return false;
        } else {
            return subMap.containsKey(id1);
        }
    }


    public double get(int id0, int id1) {
        if(id0>id1) {
            int temp = id0;
            id0 = id1;
            id1 = temp;
        }
        Map<Integer, Double> subMap = get(id0);
        if(subMap == null) {
            return Double.NaN;
        } else {
            Double aDouble = subMap.get(id1);
            return aDouble == null ? Double.NaN : aDouble;
        }
    }


    public int getCount() {
        return count;
    }
}
