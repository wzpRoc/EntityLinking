package org.ailab.wimfra.util.cache;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-15
 * Time: 下午3:16
 */
public class Node<KeyType, ValueType> {
    private KeyType key;
    private ValueType value;
    private Node<KeyType, ValueType> previousNode;
    private Node<KeyType, ValueType> nextNode;

    public Node(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }


    public KeyType getKey() {
        return key;
    }

    public void setKey(KeyType key) {
        this.key = key;
    }

    public ValueType getValue() {
        return value;
    }

    public void setValue(ValueType value) {
        this.value = value;
    }

    public Node<KeyType, ValueType> getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node<KeyType, ValueType> previousNode) {
        this.previousNode = previousNode;
    }

    public Node<KeyType, ValueType> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<KeyType, ValueType> nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }
}
