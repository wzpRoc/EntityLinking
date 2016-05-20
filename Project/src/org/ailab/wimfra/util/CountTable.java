package org.ailab.wimfra.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lu Tingming
 * Time: 2010-11-11 20:53:54
 * Desc: 计数器
 */
public class CountTable extends HashMap<Object, Int> {
    private int total = 0;

    public int add(Object key) {
        total++;

        Int value = this.get(key);
        int i;
        if (value == null) {
            i = 1;
            this.put(key, new Int(i));
        } else {
            value.i++;
            i = value.i;
        }

        return i;
    }

    public int getCount(Object key) {
        Int intObj = get(key);
        if (intObj == null) {
            return 0;
        } else {
            return intObj.i;
        }
    }

    public void print() {
        System.out.println("size=" + this.size());
        for (Map.Entry entry : this.entrySet()) {
            System.out.println(entry.getKey() + "\t\t" + ((Int) entry.getValue()).i);
        }
    }

    public int getTotal() {
        return total;
    }


    /**
     * 生成降序列表
     */
    public List<ObjectAndCount> toDescOrderList(int maxRecords) {
        int size;
        if (maxRecords <= 0) {
            size = this.size();
        } else {
            size = Math.min(maxRecords, size());
        }

        List<ObjectAndCount> list = new ArrayList<ObjectAndCount>(size);

        for (Map.Entry<Object, Int> entry : this.entrySet()) {
            int count = entry.getValue().i;
            int rank = 0;
            for (int i = list.size() - 1; i >= 0; i--) {
                int countAtI = list.get(i).count;
                if(count>countAtI) {
                    // 继续向前
                    continue;
                } else if(count <= countAtI) {
                    // 排在当前节点后面
                    rank = i + 1;
                    break;
                }
            }
            if(maxRecords == -1) {
                // 长度不限
                list.add(rank, new ObjectAndCount(entry.getKey(), count));
            } else {
                // 长度有限
                if(list.size() < maxRecords) {
                    // 还有空间
                    list.add(rank, new ObjectAndCount(entry.getKey(), count));
                } else {
                    // 没有空间
                    if(rank > maxRecords - 1) {
                        // 排不上名
                    } else {
                        // 移除最后一个
                        list.remove(maxRecords - 1);
                        list.add(rank, new ObjectAndCount(entry.getKey(), count));
                    }
                }
            }
        }

        return list;
    }

    /**
     * 生成列表
     */
    public List<ObjectAndCount> toList() {
        List<ObjectAndCount> list = new ArrayList<ObjectAndCount>();
        for (Map.Entry<Object, Int> entry : this.entrySet()) {
            list.add(new ObjectAndCount(entry.getKey(), entry.getValue().i));
        }
        return list;
    }


    public Object getKeyWithMaxCount() {
        if(size() == 0) {
            return null;
        } else if(size() == 1) {
            return keySet().iterator().next();
        } else {
            Object keyWithMaxCount = null;
            int maxCount = 0;
            for(Map.Entry entry : entrySet()) {
                int count = ((Int)entry.getValue()).i;
                if(count>maxCount) {
                    maxCount = count;
                    keyWithMaxCount = entry.getKey();
                }
            }
            return keyWithMaxCount;
        }
    }
}
