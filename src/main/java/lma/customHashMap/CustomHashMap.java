package lma.customHashMap;

import lombok.*;

import java.util.NoSuchElementException;

public class CustomHashMap<K, V> {

    @Data
    @AllArgsConstructor
    static class Node<K, V> {
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private final int hash;

        private final K key;

        private V value;

        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private Node<K, V> next;
    }

    private static final int DEFAULT_CAPACITY = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int MAXIMUM_CAPACITY = 1 << 30;

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
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
        put(key, value);
        return null;
    }

    public V remove(K key) {
        if (!containsKey(key)) {
            throw new NoSuchKeyException();
        }
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