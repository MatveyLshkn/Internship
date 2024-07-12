package lma.customHashMap;

import java.util.NoSuchElementException;

public class CustomHashMap<K, V> {

    static class Node<K, V> {

        private final int hash;

        private final K key;

        private V value;

        private Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public int getHash() {
            return hash;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                   "key=" + key +
                   ", value=" + value +
                   '}';
        }
    }

    private static final int DEFAULT_CAPACITY = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] elements;

    private int size;

    private int capacity;

    private float loadFactor;

    private int threshold;

    public CustomHashMap(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        elements = new Node[capacity];
        threshold = (int) (capacity * loadFactor);
    }

    public CustomHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    private int calculatePos(K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode() & (capacity - 1);
    }

    private void addNode(K key, V value, int hash) {
        if (size == threshold) {
            resize();
        }
        size++;
        int pos = calculatePos(key);
        if (elements[pos] == null) {
            Node<K, V> node = new Node<>(hash, key, value, null);
            elements[pos] = node;
        } else {
            Node<K, V> node = elements[pos];
            while (node != null) {
                if (node.getKey() == null) {
                    V oldValue = node.getValue();
                    node.setValue(value);
                    return;
                }
                node = node.next;
            }
            Node<K, V> newNode = new Node<>(hash, key, value, elements[pos]);
            elements[pos] = newNode;
        }
    }

    private void putForNullKey(V value) {
        addNode(null, value, 0);
    }

    public void put(K key, V value) {
        if (key == null) {
            putForNullKey(value);
        }
        addNode(key, value, key.hashCode());
    }

    public V get(K key) {
        int index = calculatePos(key);
        Node<K, V> node = elements[index];
        while (node != null) {
            if (node.getKey() == key || node.getKey().equals(key)) {
                return node.getValue();
            }
            node = node.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = calculatePos(key);
        Node<K, V> node = elements[index];
        while (node != null) {
            if (node.getKey() == key || node.getKey().equals(key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (Node<K, V> node : elements) {
            while (node != null) {
                if (node.getValue() == value || node.getValue().equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    public V replace(K key, V value) {
        if (!containsKey(key)) throw new NoSuchElementException("No such key!");
        put(key, value);
        return null;
    }

    public V remove(K key) {
        if (!containsKey(key)) throw new NoSuchElementException("No such key!");
        size--;
        int pos = calculatePos(key);
        Node<K, V> currNode = elements[pos];
        Node<K, V> prevNode = null;
        while (currNode != null) {
            if (currNode.getKey() == key || currNode.getKey().equals(key)) {
                if (prevNode == null) {
                    elements[pos] = currNode.next;
                } else {
                    prevNode.next = currNode.next;
                }
                return currNode.getValue();
            }
            prevNode = currNode;
            currNode = currNode.next;
        }
        return null;
    }

    private void resize() {
        Node<K, V>[] oldElements = elements;
        elements = new Node[capacity * 2];
        threshold = (int) (capacity * loadFactor);
        size = 0;
        for (Node<K, V> node : oldElements) {
            while (node != null) {
                put(node.getKey(), node.getValue());
                node = node.next;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getLoadFactor() {
        return loadFactor;
    }
}
