package lma.cache;

import lombok.*;

import java.util.*;


public class LRUCache<K, V> {

    private final Map<K, V> cache;

    @Setter
    @Getter
    private int capacity;

    @Getter
    private int size;

    public LRUCache(int capacity) {
        cache = new LinkedHashMap(capacity * 2, 0.75f, true);
        this.capacity = capacity;
    }

    private void removeLast() {
        remove(cache.keySet().iterator().next());
    }

    public void put(K key, V value) {
        if (!cache.containsKey(key)) {
            size++;
        }
        cache.put(key, value);
        if (size > capacity) {
            removeLast();
        }
    }

    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public V remove(K key) {
        size--;
        return cache.remove(key);
    }

    public void clear() {
        cache.clear();
        size = 0;
    }

    @Override
    public String toString() {
        return "LRUCache{" +
               "cache=" + cache +
               '}';
    }
}