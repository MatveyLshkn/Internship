package lma.customHashMap;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MapUtil {

    <K, V> void resize(CustomHashMap<K, V> map) {
        if (map.capacity == CustomHashMap.MAXIMUM_CAPACITY) {
            map.threshold = Integer.MAX_VALUE;
            return;
        }
        map.capacity = map.capacity * 2;
        if (map.capacity > CustomHashMap.MAXIMUM_CAPACITY) {
            map.capacity = CustomHashMap.MAXIMUM_CAPACITY;
        }
        CustomHashMap.Node<K, V>[] oldElements = map.elements;
        map.elements = new CustomHashMap.Node[map.capacity];
        map.threshold = (int) (map.capacity * map.loadFactor);
        map.size = 0;
        for (CustomHashMap.Node<K, V> node : oldElements) {
            while (node != null) {
                map.put(node.getKey(), node.getValue());
                node = node.getNext();
            }
        }
    }

    <K, V> void addNode(CustomHashMap<K, V> map, K key, V value, int hash) {
        if (map.size == map.threshold) {
            resize(map);
        }
        map.size++;
        int pos = calculatePos(map, key);
        if (map.elements[pos] == null) {
            CustomHashMap.Node<K, V> node = new CustomHashMap.Node<>(hash, key, value, null);
            map.elements[pos] = node;
        } else {
            CustomHashMap.Node<K, V> node = map.elements[pos];
            while (node != null) {
                if (node.getKey() == key || (node.getKey() != null && node.getKey().equals(key))) {
                    V oldValue = node.getValue();
                    node.setValue(value);
                    return;
                }
                node = node.getNext();
            }
            CustomHashMap.Node<K, V> newNode = new CustomHashMap.Node<>(hash, key, value, map.elements[pos]);
            map.elements[pos] = newNode;
        }
    }

    <K, V> int calculatePos(CustomHashMap<K, V> map, K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode() & (map.capacity - 1);
    }

    <K, V> V removeNode(CustomHashMap<K, V> map, K key) {
        map.size--;
        int pos = calculatePos(map, key);
        CustomHashMap.Node<K, V> currNode = map.elements[pos];
        CustomHashMap.Node<K, V> prevNode = null;
        while (currNode != null) {
            if (currNode.getKey() == key || (currNode.getKey() != null && currNode.getKey().equals(key))) {
                if (prevNode == null) {
                    map.elements[pos] = currNode.getNext();
                } else {
                    prevNode.setNext(currNode.getNext());
                }
                return currNode.getValue();
            }
            prevNode = currNode;
            currNode = currNode.getNext();
        }
        return null;
    }

}
