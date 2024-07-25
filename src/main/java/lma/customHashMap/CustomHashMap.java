package lma.customHashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

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

    @Data
    @AllArgsConstructor
    static class Entry<K, V> {
        private K key;
        private V value;
    }

    private static final int DEFAULT_CAPACITY = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int MAXIMUM_CAPACITY = 1 << 30;

    Set<Entry<K, V>> entries;

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

    public Set<Entry<K, V>> entrySet() {
        if(entries == null) {
            entries = new HashSet<>();
            for (int i = 0; i < elements.length; i++) {
                Node<K, V> node = elements[i];
                while (node != null) {
                    entries.add(new Entry<>(node.getKey(), node.getValue()));
                    node = node.next;
                }
            }
        }
        return entries;
    }

    public Set<K> keySet() {
        entrySet();
        Set<K> keySet = new HashSet<>();
        for(Entry<K, V> entry : entries) {
            keySet.add(entry.getKey());
        }
        return keySet;
    }

    public Set<V> valueSet() {
        entrySet();
        Set<V> valueSet = new HashSet<>();
        for(Entry<K, V> entry : entries) {
            valueSet.add(entry.getValue());
        }
        return valueSet;
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