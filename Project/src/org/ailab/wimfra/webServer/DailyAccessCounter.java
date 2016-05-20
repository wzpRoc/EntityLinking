package org.ailab.wimfra.webServer;


import org.ailab.common.config.ConfigBL;
import org.ailab.wimfra.util.CountTable;
import org.ailab.wimfra.util.time.TimeUtil;

/**
 * User: lutingming
 * Date: 13-11-28
 * Time: 下午1:13
 * Desc: 每日访问计数器
 * 主要逻辑为：按照给定键值增加计数，但是如果日期有变化，那么计数清零。
 */
public class DailyAccessCounter {
    private String name;
    private String keyName;
    private String currentDate;
    private final CountTable counter;
    private final int maxAccessCount;


    /**
     * 构造函数
     */
    public DailyAccessCounter(String name, String keyName, int maxAccessCount) {
        this.name = name;
        this.keyName = keyName;
        this.maxAccessCount = maxAccessCount;
        currentDate = TimeUtil.getYyyy_mm_dd();
        counter = new CountTable();
    }


    /**
     * 增加一次访问次数
     * 返回该用户访问次数与阈值的差
     */
    public int increase(int key) {
        return increase(String.valueOf(key));
    }


    /**
     * 增加一次访问次数
     * 返回该用户访问次数与阈值的差
     */
    public int increase(String key) {
        checkAndUpdateCurrentDate();
        return counter.add(key) - maxAccessCount;
    }


    /**
     * 检查是否已经过了一天，如果是，那么清空计数器
     */
    protected void checkAndUpdateCurrentDate() {
        String currentDate = TimeUtil.getYyyy_mm_dd();
        if(!currentDate.equals(this.currentDate)) {
            this.currentDate = currentDate;
            counter.clear();
        }
    }


    public String getCurrentDate() {
        return currentDate;
    }

    public CountTable getCounter() {
        return counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
