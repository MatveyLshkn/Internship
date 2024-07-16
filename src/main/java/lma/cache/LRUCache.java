package lma.cache;

import lombok.*;

import java.util.*;


public class LRUCache<K, V> implements Cache<K, V> {

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

    @Override
    public void put(K key, V value) {
        if (!cache.containsKey(key)) {
            size++;
        }
        cache.put(key, value);
        if (size > capacity) {
            removeLast();
        }
    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public V remove(K key) {
        size--;
        return cache.remove(key);
    }

    @Override
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