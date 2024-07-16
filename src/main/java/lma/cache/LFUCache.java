package lma.cache;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

public class LFUCache<K, V> implements Cache<K, V>{

    private final Map<K, V> cache;

    private final Map<K, Integer> usageCount;

    private final TreeMap<Integer, List<K>> map;

    @Setter
    @Getter
    private int capacity;

    @Getter
    private int size;

    public LFUCache(int capacity) {
        cache = new HashMap<>(capacity);
        usageCount = new HashMap<>(capacity);
        map = new TreeMap<>();
        this.capacity = capacity;
    }

    private V removeFirstWithLeastCount() {
        List<K> list = map.get(map.firstKey());
        K removed = list.remove(0);
        V removedData = cache.remove(removed);
        usageCount.remove(removed);
        if (list.isEmpty()) {
            map.remove(map.firstKey());
        }
        size--;
        return removedData;
    }

    @Override
    public void put(K key, V value) {
        if (size >= capacity) {
            removeFirstWithLeastCount();
        }
        if (cache.containsKey(key)) {
            remove(key);
        }
        cache.put(key, value);
        usageCount.put(key, 1);
        if (!map.containsKey(1)) {
            map.put(1, new LinkedList<>());
        }
        map.get(map.firstKey()).add(key);
        size++;
    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    private void updateCount(K key) {
        Integer count = usageCount.remove(key);
        usageCount.put(key, count + 1);
        map.get(count).remove(key);
        if (!map.containsKey(count + 1)) {
            map.put(count + 1, new LinkedList<>());
        }
        map.get(count + 1).add(key);
    }

    @Override
    public V get(K key) {
        if (cache.containsKey(key)) {
            updateCount(key);
            return cache.get(key);
        }
        return null;
    }

    @Override
    public V remove(K key) {
        V data = cache.remove(key);
        Integer count = usageCount.remove(key);
        map.get(count).remove(key);
        size--;
        if (map.get(count).isEmpty()) {
            map.remove(count);
        }
        return data;
    }

    @Override
    public void clear() {
        cache.clear();
        usageCount.clear();
        map.clear();
        size = 0;
    }

    @Override
    public String toString() {
        return "LFUCache{" +
               "cache=" + cache +
               '}';
    }
}