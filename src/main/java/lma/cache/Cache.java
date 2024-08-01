package lma.cache;

public interface Cache<K, V> {

    public void put(K key, V value);

    public boolean contains(K key);

    public V get(K key);

    public V remove(K key);

    public void clear();
}
