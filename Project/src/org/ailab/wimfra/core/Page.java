package org.ailab.wimfra.core;

import org.ailab.wimfra.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * User: Lu Tingming
 * Time: 2010-01-5
 * Desc: 分页查询中的一页
 */
public class Page {

    public static final int MAXRECORDNUMPERPAGE = 200;
    /**
     * 记录列表
     */
    protected List list;

    /**
     * 一页中的记录数
     */
    protected int recordNumPerPage = 10;

    /**
     * 当前页码（从1开始计数）
     */
    protected int currentPageNo = 1;

    /**
     * 总记录数
     */
    protected int totalRecordNum;

    /**
     * 查询字段
     */
    protected String fileds;

    /**
     * 排序字段
     */
    protected String orderBy;

    /**
     * 排序方式
     */
    protected String order;

    /**
     * 查询条件
     */
    protected String whereCondition;

    // id字段名
    protected String idFieldName = "id";

    // 表名
    protected String tableName;
    protected String sqlForCount;
    protected String sqlForGetList;

    protected List<JoinConfig> joinConfigList;

    // id值列表
    // 只要这个字段不为null，那么启动这个功能
    protected ArrayList idValueList;


    public Page() {
    }


    public Page(int recordNumPerPage) {
        this.recordNumPerPage = recordNumPerPage;
    }

    /**
     * 得到记录表
     *
     * @return 记录表
     */
    public List getList() {
        return list;
    }

    /**
     * 设置记录表
     *
     * @param list 记录表
     */
    public void setList(List list) {
        this.list = list;
    }

    /**
     * 得到一页中的记录数
     *
     * @return 一页中的记录数
     */
    public int getRecordNumPerPage() {
        return recordNumPerPage;
    }

    /**
     * 设置一页中的记录数
     *
     * @param recordNumPerPage 一页中的记录数
     */
    public void setRecordNumPerPage(int recordNumPerPage) {
        if (recordNumPerPage == 0) {
            this.recordNumPerPage = 20;
        } else {
            this.recordNumPerPage = recordNumPerPage;
        }

    }

    /**
     * 设置一页中的记录数
     * 用于处理用户输入非法数据
     * 如果用户输入的数据不是数字类型则会调用此方法 将
     *
     * @param recordNumPerPage 一页中的记录数
     */
    public void setRecordNumPerPage(String recordNumPerPage) {
        int value = 20;
        if (!StringUtil.isEmpty(recordNumPerPage)) {
            try {
                value = Integer.parseInt(recordNumPerPage);
            } catch (Exception e) {
                value = 20;
            }
        }
        //如果输入的数字为0将其转化为20
        if (value <= 0 || value > 200) {
            value = 20;
        }
        this.recordNumPerPage = value;
    }

    /**
     * 得到当前页码
     *
     * @return 当前页码
     */
    public int getCurrentPageNo() {
        return currentPageNo;
    }

    /**
     * 设置当前页码
     *
     * @param currentPageNo 当前页码
     */
    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    /**
     * 得到总记录数
     *
     * @return 总记录数
     */
    public int getTotalRecordNum() {
        return totalRecordNum;
    }

    /**
     * 设置总记录数
     *
     * @param totalRecordNum 总记录数
     */
    public void setTotalRecordNum(int totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    /**
     * 得到总页数
     *
     * @return 总页数
     */
    public int getTotalPageNum() {
        return ((totalRecordNum - 1) / recordNumPerPage) + 1;
    }

    /**
     * 得到查询字段
     *
     * @return 总页数
     */
    public String getFileds() {
        return fileds;
    }

    /**
     * 设置查询字段
     *
     * @param fileds 查询字段
     */
    public void setFileds(String fileds) {
        this.fileds = fileds;
    }

    /**
     * 得到排序方式
     *
     * @return 排序方式
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置排序方式
     *
     * @param orderBy 排序方式
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 得到查询条件
     *
     * @return 查询条件
     */
    public String getWhereCondition() {
        return whereCondition;
    }

    /**
     * 设置查询条件
     *
     * @param whereCondition 查询条件
     */
    public void setWhereCondition(String whereCondition) {
        this.whereCondition = whereCondition;
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    public ArrayList getIdValueList() {
        return idValueList;
    }

    public void setIdValueList(ArrayList idValueList) {
        this.idValueList = idValueList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 由页码设置新的当页事件列表
     * 开发人：彭程
     * 开发日期： 2011-10-29
     *
     * @param list
     */
    public void setNewList(List list) {
        if (list == null)
            return;
        // 设置总数
        totalRecordNum = list.size();
        // 逐项设置this.list的值
        // 首先计算开始位置和结束位置
        int start = (currentPageNo - 1) * recordNumPerPage;
        // 如果开始位置已经大于结束位置，那么说明翻页出了问题，程序直接结束
        if (start > totalRecordNum) return;
        // 计算结束位置
        int end = ((start + recordNumPerPage) > totalRecordNum) ? totalRecordNum : (start + recordNumPerPage);
        this.list = new ArrayList();
        // 在page的list中装载新数据
        for (int i = start; i < end; i++) {
            this.list.add(list.get(i));
        }
    }

    public String getSqlForCount() {
        return sqlForCount;
    }

    public void setSqlForCount(String sqlForCount) {
        this.sqlForCount = sqlForCount;
    }

    public String getSqlForGetList() {
        return sqlForGetList;
    }

    public void setSqlForGetList(String sqlForGetList) {
        this.sqlForGetList = sqlForGetList;
    }

    /**
     * 获取当前页的开始记录序号（从0编号）
     *
     * @return
     */
    public int getStartRecordIndex() {
        int start = (currentPageNo - 1) * recordNumPerPage;
        return (start > totalRecordNum) ? totalRecordNum - 1 : start;
    }

    /**
     * 获取当前页的结束记录序号（从0编号）
     *
     * @return
     */
    public int getEndRecordIndex() {
        int end = currentPageNo * recordNumPerPage - 1;
        return (end > totalRecordNum) ? totalRecordNum - 1 : end;
    }


    /**
     * 返回当前页的记录数
     */
    public int getRecordNumInCurrentPage() {
        return getEndRecordIndex() - getStartRecordIndex() + 1;
    }


    /**
     * 如果当前页码大于总页码，则当前页码设为总页码
     */
    public void checkCurrentPageNo() {
        int totalPageNum = getTotalPageNum();
        if (currentPageNo > totalPageNum) {
            currentPageNo = totalPageNum;
        }
    }


    /**
     * 增加一个表连接设置
     */
    public void addJoinConfig(JoinConfig joinConfig) {
        if(joinConfigList == null) {
            joinConfigList = new ArrayList<JoinConfig>();
        }
        joinConfigList.add(joinConfig);
    }


    public List<JoinConfig> getJoinConfigList() {
        return joinConfigList;
    }

    public void setJoinConfigList(List<JoinConfig> joinConfigList) {
        this.joinConfigList = joinConfigList;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
