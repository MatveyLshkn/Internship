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

    Node<K, V>[] elements;

    int size;

    int capacity;

    float loadFactor;

    int threshold;

    public CustomHashMap(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        elements = new Node[capacity];
        threshold = (int) (capacity * loadFactor);
    }

    public CustomHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    private void putForNullKey(V value) {
        MapUtil.addNode(this, null, value, 0);
    }

    public void put(K key, V value) {
        if (key == null) {
            putForNullKey(value);
        } else {
            MapUtil.addNode(this, key, value, key.hashCode());
        }
    }

    public V get(K key) {
        int index = MapUtil.calculatePos(this, key);
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
        int index = MapUtil.calculatePos(this, key);
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
        return MapUtil.removeNode(this, key);
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