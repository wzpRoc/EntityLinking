package org.ailab.wimfra.core;


import org.ailab.wimfra.database.DBConnect;
import org.ailab.wimfra.frontend.ValueAndLabelUtil;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;

import java.sql.SQLException;
import java.util.*;


/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: business logic base class
 */
public abstract class BaseBL implements IValueAndLabelListProvider {
    // 日志工具
    private Logger logger = Logger.getLogger(BaseBL.class);

    // 是否是自己的数据库连接
    protected boolean isOwnDBConnect = true;

    List<BaseBL> batchBLList;

    // 其他数据库连接
    protected DBConnect deliveredDBConnect;
    protected DBConnect batchDBConnect;

    // DAO
    protected BaseDAO dao;

    protected ArrayList<BaseDAO> daoList;

    // 用户语言
    protected String userLanguage;

    ////////////////////////////////////   以下为缓存相关    ///////////////////////////////////
    // 是否启用缓存（默认不启用）
    public static boolean isCacheEnabled = false;


    /**
     * 是否启用缓存
     *
     * @return
     */
    protected boolean isCacheEnabled() {
        try {
            return this.getClass().getField("isCacheEnabled").getBoolean(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 得到DTO列表
     */
    protected List getDtoListFromCache() throws NoSuchFieldException, IllegalAccessException {
        return (List) this.getClass().getField("dtoList").get(null);
    }


    /**
     * 得到DTO列表
     */
    public static List getDtoListFromCache(BaseBL bl) throws NoSuchFieldException, IllegalAccessException {
        return (List) bl.getClass().getField("dtoList").get(null);
    }


    /**
     * 得到ID到DTO的映射
     *
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected Map getIdToDtoMapFromCache() throws NoSuchFieldException, IllegalAccessException {
        return (Map) this.getClass().getField("idToDtoMap").get(null);
    }


    /**
     * 得到ID到DTO的映射
     *
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected BaseDTO getByIdFromCache(int id) throws NoSuchFieldException, IllegalAccessException {
        return getByIdFromCache(this.getClass(), id);
    }


    /**
     * 得到ID到DTO的映射
     *
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static BaseDTO getByIdFromCache(Class clazz, int id) throws NoSuchFieldException, IllegalAccessException {
        Map map = (Map) clazz.getField("idToDtoMap").get(null);
        return (BaseDTO) map.get(id);
    }


    /**
     * 重新载入缓存
     */
    protected static void reloadCache(BaseBL bl) {
        try {
            List<BaseDTO> dtoList = bl.getList();
            bl.getClass().getField("dtoList").set(null, dtoList);

            Map<Integer, BaseDTO> idToDtoMap = new HashMap(dtoList.size());
            for (BaseDTO baseDTO : dtoList) {
                idToDtoMap.put(baseDTO.getId(), baseDTO);
            }
            bl.getClass().getField("idToDtoMap").set(null, idToDtoMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 重新载入缓存
     */
    protected void reloadCache() {
        BaseBL.reloadCache(this);
    }
    ////////////////////////////////////   以上为缓存相关    ///////////////////////////////////

    /**
     * 由id得到一条记录
     *
     * @param id id
     * @return BaseDTO
     */
    public BaseDTO get(int id) {
        return dao.getById(id);
    }


    /**
     * 由id得到一条记录
     *
     * @param id id
     * @return BaseDTO
     */
    public BaseDTO get(String id) throws SQLException {
        return get(Integer.parseInt(id));
    }


    /**
     * 得到多条记录
     *
     * @param page
     */
    public void getList(Page page) throws Exception {
        dao.getList(page);
    }


    /**
     * 得到多条记录
     */
    public List getList() {
        return dao.getList();
    }


    /**
     * 得到多条记录
     */
    public List getList(String sql, Object... params) throws SQLException {
        return dao.getList(sql, params);
    }


    /**
     * 得到多条记录
     */
    public List getListByCondition(String condition, Object... params) {
        return dao.getListByCondition(condition, params);
    }

    /**
     * 得到多条记录
     */
    public List getLimitedList(int limit) {
        return dao.getLimitedList(limit);
    }


    /**
     * 得到多条记录
     */
    public List getListBySql(String sql, Object... params) throws SQLException {
        return dao.getListBySqlAndParams(sql, params);
    }


    public int getCount() throws SQLException {
        return dao.getCount();
    }


    public int getCount(String condition, Object... params) throws SQLException {
        return dao.getCount(condition, params);
    }


    public List getListByIdCollection(Collection idCollection) {
        return dao.getList(idCollection);
    }

    public List getListByIdCollection(Collection idCollection, String fields) throws Exception {
        return dao.getList(idCollection, fields);
    }

    public Map getIdToDtoMap(Collection idCollection) throws Exception {
        Map map = new HashMap();
        if (idCollection == null || idCollection.size() == 0) {
            return map;
        }

        final List list = dao.getList(idCollection);
        for (Object obj : list) {
            BaseDTO dto = (BaseDTO) obj;
            map.put(dto.getId(), dto);
        }
        return map;
    }


    /**
     * 初始化一条记录
     */
    public void initDto(BaseDTO dto) throws InterruptedException {

    }


    /**
     * 插入一条记录
     */
    public int insert(BaseDTO dto) {
        if (dto.getId() == 0) {
            dto.setId(getNextId());
        }

        int affectedRowsCount = dao.insert(dto);

        if (affectedRowsCount > 0 && isCacheEnabled()) {
            reloadCache();
        }

        return affectedRowsCount;
    }


    /**
     * 插入一条记录
     */
    public int insertAfterAssigningId(BaseDTO dto) throws Exception {
        dto.setId(getNextId());
        return insert(dto);
    }


    /**
     * 获得下一个ID
     */
    public int getNextId() {
        return dao.getNextId();
    }


    /**
     * 插入多条记录
     */
    public int insert(Collection collection) {
        if (collection == null) return 0;

        int affectedRowsCount = 0;
        for (Object dto : collection) {
            affectedRowsCount += insert((BaseDTO) dto);
        }

        return affectedRowsCount;
    }


    /**
     * 插入多条记录
     */
    public int batchInsert(Collection collection) throws Exception {
        int affectedRowsCount = dao.batchInsert(collection);

        if (isCacheEnabled()) {
            reloadCache(this);
        }

        return affectedRowsCount;
    }


    /**
     * 插入多条记录
     */
    public int insertAfterAssigningId(Collection collection) throws Exception {
        for (Object obj : collection) {
            BaseDTO dto = (BaseDTO) obj;
            dto.setId(getNextId());
        }

        return insert(collection);
    }


    /**
     * 插入多条记录
     */
    public int batchInsertAfterAssigningId(Collection collection) throws Exception {
        for (Object obj : collection) {
            BaseDTO dto = (BaseDTO) obj;
            dto.setId(getNextId());
        }

        return batchInsert(collection);
    }


    /**
     * 更新多条记录
     */
    public int update(Collection collection) throws Exception {
        int affectedRowsCount = 0;
        for (Object dto : collection) {
            affectedRowsCount += update((BaseDTO) dto);
        }

        return affectedRowsCount;
    }


    /**
     * 更新多条记录
     */
    public int batchUpdate(Collection collection) throws Exception {
        return dao.batchUpdate(collection);
    }


    /**
     * 修改一条记录
     */
    public int update(BaseDTO dto) throws Exception {
        int affectedRowsCount = dao.update(dto);

        if (affectedRowsCount > 0 && isCacheEnabled()) {
            reloadCache();
        }

        return affectedRowsCount;
    }


    /**
     * 删除一条记录
     */
    public int delete(int id) throws Exception {
        int affectedRowsCount = dao.delete(id);

        if (affectedRowsCount > 0 && isCacheEnabled()) {
            reloadCache();
        }

        return affectedRowsCount;
    }


    /**
     * 删除一条或多条记录
     */
    public int delete(String ids) throws Exception {
        return dao.deleteByKeys(ids);
    }


    /**
     * 清除表中所有内容
     */
    public int truncate() throws SQLException {
        return dao.truncate();
    }


    /**
     * 清除表中所有内容
     */
    public int deleteAll() throws SQLException {
        return dao.deleteAll();
    }


    /**
     * 清除备份表中所有内容
     */
    public int truncateBackupTable() throws SQLException {
        return dao.truncateBackupTable();
    }


    /**
     * 创建备份表
     */
    public int backup() throws SQLException {
        return dao.backup();
    }


    /**
     * 创建备份表
     */
    public int backup(boolean withTime) throws SQLException {
        return dao.backup(withTime);
    }


    /**
     * 重新备份（先删除再创建）备份表
     */
    public int reBackup() throws SQLException {
        return dao.reBackup();
    }


    /**
     * 判断当前数据库中是否存在指定的表
     */
    public boolean existsBackupTable() throws SQLException {
        return dao.existsBackupTable();
    }


    /**
     * 返回“值-文字”列表
     *
     * @return
     */
    public List<IValueAndLabel> getValueAndTextList() throws Exception {
        List<BaseDTO> dtoList = getDtoListFromCache();
        return ValueAndLabelUtil.dtoListToVTList(dtoList);
    }


    /*****************************************************************************
     批量操作
     *****************************************************************************/
    /**
     * 设置自动提交
     *
     * @param b
     * @throws java.sql.SQLException
     */
    /*
    public void setAutoCommit(boolean b) throws SQLException {
        dao.getDBConnect().setAutoCommit(b);
    }
    */

    /**
     * 提交
     */
    /*
    public void commit() throws SQLException {
        dao.getDBConnect().commit();
        setAutoCommit(true);
    }
    */


    /**
     * 开始批量操作模式
     *
     * @throws java.sql.SQLException
     */
    public void beginBatch() throws SQLException {
        logger.debug("beginBatch");
        if (isOwnDBConnect) {
            batchDBConnect = new DBConnect(getDbConnectConfig());
            batchDBConnect.setAutoCommit(false);
        }
        initDAOList();

        for (BaseDAO dao : daoList) {
            if (isOwnDBConnect) {
                // 使用自己的数据库连接
                dao.beginBatch(batchDBConnect);
            } else {
                // 使用传入的数据库连接
                dao.beginBatch(deliveredDBConnect);
            }
        }

        if (batchBLList != null) {
            for (BaseBL anotherBL : batchBLList) {
                if (isOwnDBConnect) {
                    // 使用自己的数据库连接
                    anotherBL.beginBatch(batchDBConnect);
                } else {
                    // 使用传入的数据库连接
                    anotherBL.beginBatch(deliveredDBConnect);
                }
            }
        }
    }

    protected void initDAOList() {
        if (daoList == null) {
            daoList = new ArrayList<BaseDAO>();
            daoList.add(dao);
        }
    }


    protected void registerAdditionalDAO(BaseDAO... additionalDAOs) {
        initDAOList();
        for (BaseDAO additionalDAO : additionalDAOs) {
            daoList.add(additionalDAO);
        }
    }

    /**
     * 开始批量操作模式，传入数据库连接
     *
     * @throws java.sql.SQLException
     */
    public void beginBatch(DBConnect deliveredDBConnect) throws SQLException {
        isOwnDBConnect = false;
        this.deliveredDBConnect = deliveredDBConnect;
        beginBatch();
    }


    /**
     * 开始批量操作模式，传入数据库连接
     *
     * @throws java.sql.SQLException
     */
    public void beginBatch(BaseBL deliveredBLBase) throws SQLException {
        isOwnDBConnect = false;
        this.deliveredDBConnect = deliveredBLBase.dao.getDBConnect();
        beginBatch();
    }


    /**
     * 结束批量操作模式
     *
     * @throws java.sql.SQLException
     */
    public void commitBatch() throws Exception {
        logger.debug("commitBatch");
        if (isOwnDBConnect) {
            batchDBConnect.commit();
            batchDBConnect.setAutoCommit(true);
            batchDBConnect.close();
        }

        for (BaseDAO dao : daoList) {
            dao.commitBatch();
        }

        if (batchBLList != null) {
            for (BaseBL anotherBL : batchBLList) {
                anotherBL.commitBatch();
            }
        }
    }


    /**
     * 结束批量操作模式
     *
     * @throws java.sql.SQLException
     */
    public void rollbackBatch() throws Exception {
        logger.debug("rollbackBatch");
        for (BaseDAO dao : daoList) {
            dao.rollbackBatch();
        }
    }


    /**
     * 传递数据库连接
     */
    public void deliverDBConnect(BaseBL deliveredBLBase) {
        // 防止daoList为空
        initDAOList();
        for (BaseDAO dao : daoList) {
            dao.deliverDBConnect(deliveredBLBase.dao.getDBConnect());
        }
    }


    /**
     * 传递数据库连接
     *
     * @param deliveredDBConnect
     */
    public void deliverDBConnect(DBConnect deliveredDBConnect) {
        // 防止daoList为空
        initDAOList();
        for (BaseDAO dao : daoList) {
            dao.deliverDBConnect(deliveredDBConnect);
        }
    }


    /**
     * 结束传递，以关闭数据库连接
     */
    public void endDeliver() {
        dao.closeDBConnect();
    }

    public void setAutoCommit(boolean b) throws SQLException {
        dao.getDBConnect().setAutoCommit(b);
    }

    public void commit() throws SQLException {
        logger.debug("commit");
        dao.getDBConnect().commit();
    }

    public void rollback() throws SQLException {
        logger.debug("rollback");
        dao.getDBConnect().rollback();
    }

    /*****************************************************************************
     用户语言操作
     *****************************************************************************/
    /**
     * 设置用户语言
     *
     * @param userLanguage
     */
    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    /**
     * 根据key得到（用户界面的）消息字符串
     *
     * @param messageKey
     * @return
     */
    protected String getMessageString(String messageKey) {
        if (userLanguage == null || "".equals(userLanguage)) {
            userLanguage = "en_US";
        }
        return messageKey;
    }


    public String getTableName() {
        return dao.getTableName();
    }


    public String getDbConnectConfig() {
        return dao.dbConnectConfig;
    }


    /**
     * 设置数据库连接的配置
     *
     * @param dbConnectConfig
     */
    public void setDbConnectConfig(String dbConnectConfig) {
        if (daoList == null) {
            this.dao.dbConnectConfig = dbConnectConfig;
        } else {
            for (BaseDAO dao : daoList) {
                dao.dbConnectConfig = dbConnectConfig;
            }
        }
    }

    public void addBatchBL(BaseBL anotherBL) {
        if (batchBLList == null) {
            batchBLList = new ArrayList<BaseBL>();
        }
        batchBLList.add(anotherBL);
    }


    /**
     * 获得模块名称
     */
    public String getModule() {
        return getModule(this.getClass());
    }


    /**
     * 获得模块名称
     */
    public static String getModule(Class clazz) {
        String name = clazz.getSimpleName();
        if (name.endsWith("BL")) {
            name = name.substring(0, name.length() - 2);
        }
        return name;
    }


    /**
     * 根据开始位置、结束位置、过滤条件查询得到列表
     */
    public List getListInOffset(int start, int len, String condition, Object... params) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (StringUtil.notEmpty(condition)) {
            sb.append(condition);
        } else {
            sb.append("1=1");
        }
        sb.append(" LIMIT ").append(start).append(", ").append(len);
        return dao.getListByCondition(sb.toString(), params);
    }


    /**
     * 设置表名
     */
    public void setTableName(String tableName) {
        dao.setTableName(tableName);
    }
}
