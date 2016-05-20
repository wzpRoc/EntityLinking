package org.ailab.wimfra.core;


import java.sql.SQLException;
import java.util.List;


/**
 * User: Lu Timgming
 * Date: 2009-12-28
 * Time: 13:22:35
 * Desc: business logic base class
 */
public abstract class DocInfoBaseBL extends BaseBL {
    /**
     * 按照文档ID查询列表
     * @param docId
     * @return
     * @throws java.sql.SQLException
     */
    public List getListByDocId(int docId) throws SQLException {
        return ((DocInfoBaseDAO) dao).getListByDocId(docId);
    }


    /**
     * 按照文档ID删除
     * @param docId
     * @return
     * @throws java.sql.SQLException
     */
    public int deleteByDocId(int docId) throws SQLException {
        return ((DocInfoBaseDAO) dao).deleteByDocId(docId);
    }

}