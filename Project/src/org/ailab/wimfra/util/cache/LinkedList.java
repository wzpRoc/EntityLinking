package org.ailab.wimfra.util.cache;

/**
 * User: lutingming
 * Date: 16-1-15
 * Time: 下午3:16
 */
public class LinkedList<KeyType, ValueType> {
    private int size;
    private Node<KeyType, ValueType> headNode;
    private Node<KeyType, ValueType> tailNode;

    public Node<KeyType, ValueType> addToHead(KeyType key, ValueType value) {
        Node<KeyType, ValueType> node = new Node<KeyType, ValueType>(key, value);
        setHead(node);

        size++;

        if(size == 1) {
            tailNode = node;
        }

        return node;
    }


    public Node<KeyType, ValueType> moveToHead(Node<KeyType, ValueType> node) {
        if(node == headNode) {
            // ok
        } else if(node == tailNode) {
            // node!=headNode，那么至少有两个节点
            tailNode = node.getPreviousNode();
            tailNode.setNextNode(null);
            setHead(node);
        } else {
            // 在中间
            Node<KeyType, ValueType> previousNode = node.getPreviousNode();
            Node<KeyType, ValueType> nextNode = node.getNextNode();
            previousNode.setNextNode(nextNode);
            nextNode.setPreviousNode(previousNode);
            setHead(node);
        }

        setHead(node);

        return node;
    }


    private void setHead(Node<KeyType, ValueType> node) {
        node.setNextNode(headNode);
        if(headNode!=null) {
            headNode.setPreviousNode(node);
        }
        headNode = node;
    }


    public Node<KeyType, ValueType> removeTail() {
        if(tailNode == null) {
            throw new RuntimeException("list is null");
        }

        Node<KeyType, ValueType> originalTailNode = tailNode;

        Node<KeyType, ValueType> previousOfTail = tailNode.getPreviousNode();
        if(previousOfTail == null) {
            // list is empty
            headNode = null;
            tailNode = null;
        } else {
            tailNode = previousOfTail;
            tailNode.setNextNode(null);
        }

        size--;

        return originalTailNode;
    }

    public int size() {
        return size;
    }
}
