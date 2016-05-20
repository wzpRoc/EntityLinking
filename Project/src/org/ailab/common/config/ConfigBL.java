package org.ailab.common.config;






import org.ailab.wimfra.core.BLMessage;
import org.ailab.wimfra.core.BaseBL;
import org.ailab.wimfra.core.BaseDTO;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2012-10-13
 * 功能描述：配置业务逻辑
 */
public class ConfigBL extends BaseBL {
    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    public static boolean isCacheEnabled = true;
    public static List<Config> dtoList;
    public static Map<Integer, Config> idToDtoMap;
    public static Map<String, String> configKeyToValueMap;
    public static Map<String, String> configNameToValueMap;


    static {
        try {
            new ConfigBL().reloadCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 重新载入缓存
     */
    protected void reloadCache() {
        super.reloadCache();

        configKeyToValueMap = new HashMap<String, String>(dtoList.size());
        configNameToValueMap = new HashMap<String, String>(dtoList.size());
        for (Config config : dtoList) {
            final String module = config.module;
            final String name = config.name;
            configKeyToValueMap.put(getKey(module, name), config.getValue());
            configNameToValueMap.put(config.getName(), config.getValue());
        }
    }

    private static String getKey(String module, String name) {
        return module + " " + name;
    }
    ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////


    /**
     * 日志工具
     */
    private Logger logger = Logger.getLogger(ConfigBL.class);
    ConfigDAO dao;

    /**
     * 构造函数
     */
    public ConfigBL() {
        dao = new ConfigDAO();
        super.dao = dao;
    }




    @Override
    public int insert(BaseDTO dto) {
        Config config = (Config) dto;

        // 检查编码是否已经存在
        String configByCode = getValue(config.getModule(), config.getName());
        if(configByCode!=null) {
            throw new RuntimeException("记录["+config.getModule()+", "+ config.getName()+"]已经存在");
        }

        return super.insert(dto);
    }

    /**
     * 处理数据更新事件
     */
    public void onUpdated() {

    }


    public String getValueAndTextListDataId() {
        return "";
    }


    /**
     * 修改一条记录
     */
    public int updateValueByModuleAndName(String module, String name, String value) throws Exception {
        String originalValue = getValue(module, name);

        if (StringUtil.equals(value, originalValue)) {
            // 不需要更新数据库
            return -1;
        } else {
            final int nrAffectedRows = dao.updateValueByModuleAndName(module, name, value);
            reloadCache();
            return nrAffectedRows;
        }
    }


    /**
     * 修改一条记录
     */
    public int updateValueByName(String name, String value) throws Exception {
        final int nrAffectedRows = dao.updateValueByName(name, value);

        reloadCache();

        return nrAffectedRows;
    }


    /**
     * 根据module得到列表
     */
    public List<Config> getListByModule(String module) throws SQLException {
        return dao.getListByModule(module);
    }


    /**
     * 根据classification得到映射表（name->value）
     * 如果有异常，返回null
     */
    public HashMap<String, String> getNameToValueMapByModule(String module) throws SQLException {
        List<Config> list = dao.getListByModule(module);
        HashMap<String, String> map = new HashMap<String, String>(list.size());
        for (Config config : list) {
            map.put(config.getName(), config.getValue());
        }

        return map;
    }


    /**
     * 得到配置项的值
     */
    public String getValueFromDB(String module, String name) throws SQLException {
        return dao.getValue(module, name);
    }

    /**
     * 得到配置项的值
     */
    public String getValueFromDB(String name) throws SQLException {
        return dao.getValue(name);
    }

    /**
     * 得到配置项的值
     */
    public int getIntValueFromDB(String name, int valueIfNull) {
        try {
            String s = null;
            s = getValueFromDB(name);
            if (StringUtil.isEmpty(s)) {
                return valueIfNull;
            }
            return Integer.parseInt(s);
        } catch (Exception e) {
            logger.error(e);
            return valueIfNull;
        }
    }


    /**
     * 得到配置项的值
     *
     * @param module
     * @param name
     * @return
     */
    public static String getValue(String module, String name) {
        return configKeyToValueMap.get(getKey(module, name));
    }


    /**
     * 得到配置项的值
     *
     * @param name
     * @return
     */
    public static String getValue(String name) {
        return configNameToValueMap.get(name);
    }


    /**
     * 得到配置项的值
     *
     * @param name
     * @return
     */
    public static int getIntValue(String name) throws Exception {
        String s = configNameToValueMap.get(name);
        if (StringUtil.isEmpty(s)) {
            throw new Exception("配置项值为空:" + name);
        }
        return Integer.parseInt(s);
    }


    /**
     * 得到配置项的值
     */
    public static int getIntValue(String name, int valueIfNull) {
        if(configNameToValueMap == null) {
            return valueIfNull;
        }
        String s = configNameToValueMap.get(name);
        if (StringUtil.isEmpty(s)) {
            return valueIfNull;
        }
        return Integer.parseInt(s);
    }


    /**
     * 得到配置项的值
     */
    public static int getIntValue(String module, String name, int valueIfNull) {
        if(configKeyToValueMap == null) {
            return valueIfNull;
        }
        String s = configKeyToValueMap.get(getKey(module, name));
        if (StringUtil.isEmpty(s)) {
            return valueIfNull;
        }
        return Integer.parseInt(s);
    }


    /**
     * 得到配置项的值
     */
    public static double getDoubleValue(String module, String name, double valueIfNull) {
        String s = configKeyToValueMap.get(getKey(module, name));
        if (StringUtil.isEmpty(s)) {
            return valueIfNull;
        }
        return Double.parseDouble(s);
    }


    /**
     * 得到配置项的值
     *
     * @param name
     * @return
     */
    public static boolean getBooleanValue(String name) throws Exception {
        String value = configNameToValueMap.get(name);
        if (StringUtil.isEmpty(value)) {
            throw new Exception("配置项值为空:" + name);
        }
        return isTrue(value)
                ;
    }


    /**
     * 得到配置项的值
     */
    public static boolean getBooleanValue(String name, boolean valueIfNull) {
        if(configNameToValueMap == null) {
            return valueIfNull;
        }
        String value = configNameToValueMap.get(name);
        if (StringUtil.isEmpty(value)) {
            return valueIfNull;
        }
        return isTrue(value);
    }


    /**
     * 得到配置项的值
     */
    public static boolean getBooleanValue(String module, String name, boolean valueIfNull) {
        String value = configKeyToValueMap.get(getKey(module, name));
        if (StringUtil.isEmpty(value)) {
            return valueIfNull;
        }
        return isTrue(value);
    }


    public static boolean isTrue(String value) {
        return "1".equals(value)
                || "true".equalsIgnoreCase(value)
                || "yes".equalsIgnoreCase(value)
                || "ok".equalsIgnoreCase(value);
    }

}
