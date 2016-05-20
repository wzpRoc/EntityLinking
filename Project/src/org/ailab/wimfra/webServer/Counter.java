package org.ailab.wimfra.webServer;


import org.ailab.wimfra.util.CountTable;

/**
 * User: lutingming
 * Date: 13-11-28
 * Time: 下午1:13
 * Desc: 计数器
 */
public class Counter {
    // 最大访问次数
    private int maxCount;
    private CountTable counter;

    public Counter(int maxCount) {
        this.maxCount = maxCount;
        counter = new CountTable();
    }


    /**
     * 增加一次计数
     * 返回计数是否在阈值范围内
     */
    public boolean increaseAndCheck(Object key) {
        int count = counter.add(key);
        return count <= maxCount;
    }


    /**
     * 清空
     */
    public void clear() {
        counter.clear();
    }

    public CountTable getCounter() {
        return counter;
    }
}
