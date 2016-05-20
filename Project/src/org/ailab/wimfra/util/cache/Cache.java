package org.ailab.wimfra.util.cache;

import org.ailab.wimfra.util.NumberUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: lutingming
 * Date: 16-1-15
 * Time: 下午3:08
 * Desc: 缓存基类
 */
public abstract class Cache<KeyType, ValueType> {
    private int maxSize;
    private Map<KeyType, Node<KeyType, ValueType>> keyToNodeMap;
    private LinkedList<KeyType, ValueType> accessList;
    private Set<KeyType> emptyKeySet;

    private int getCount;
    private int hitCount;
    private int queryCount;
    private int hitEmptyCount;


    protected Cache(int maxSize) {
        this.maxSize = maxSize;

        keyToNodeMap = new HashMap<KeyType, Node<KeyType, ValueType>>();
        accessList = new LinkedList<KeyType, ValueType>();
        emptyKeySet = new HashSet<KeyType>();

        getCount = 0;
        hitCount = 0;
        queryCount = 0;
        hitEmptyCount = 0;
    }


    public ValueType get(KeyType key) {
        getCount++;
        Node<KeyType, ValueType> node = keyToNodeMap.get(key);
        if(node == null) {
            // 缓存中没有
            if(emptyKeySet.contains(key)) {
                // 之前已查询数据库验证确实没有
                hitEmptyCount++;
                return null;
            } else {
                // 查询
                queryCount++;
                ValueType value = query(key);
                if(value == null) {
                    // 确实没有（哥只能帮您到这了）
                    emptyKeySet.add(key);
                    return null;
                } else {
                    // 查询到了
                    Node<KeyType, ValueType> newNode = accessList.addToHead(key, value);
                    keyToNodeMap.put(key, newNode);
                    checkSize();
                    return value;
                }
            }
        } else {
            hitCount++;
            accessList.moveToHead(node);
            return node.getValue();
        }
    }

    protected void checkSize() {
        if (keyToNodeMap.size()>maxSize) {
            Node<KeyType, ValueType> originalTailNode = accessList.removeTail();
            keyToNodeMap.remove(originalTailNode.getKey());
        }
    }

    protected abstract ValueType query(KeyType key);

    public void printStat() {
        double rate = (hitCount+hitEmptyCount)/new Double(getCount);
        System.out.println(
                "get: "+getCount +
                "\thit: " +hitCount+
                "\thitEmpty: " +hitEmptyCount+
                "\tquery: " +queryCount+
                "\t(hit+hitEmpty)/get: " + NumberUtil.format(rate, 4)
        );
    }
}
