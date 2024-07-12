package lma.customHashMap;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MapUtilTest {

    @Test
    void resize() {
        CustomHashMap<Integer, Integer> map = new CustomHashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(null, 4);

        int prevCapacity = map.capacity;
        MapUtil.resize(map);

        assertThat(map.capacity).isEqualTo(prevCapacity * 2);
        assertThat(map.containsKey(null)).isTrue();
        assertThat(map.containsKey(5)).isFalse();
    }

    @Test
    void addNode() {
        CustomHashMap<Integer, Integer> map = new CustomHashMap<>();

        int prevSize = map.size;

        MapUtil.addNode(map, null, 4, 0);

        assertThat(map.size).isEqualTo(prevSize + 1);
        assertThat(map.containsKey(null)).isTrue();
    }

    @Test
    void calculatePos() {
        CustomHashMap<Integer, Integer> map = new CustomHashMap<>();

        Integer key = 2;

        assertThat(MapUtil.calculatePos(map, null)).isEqualTo(0);
        assertThat(MapUtil.calculatePos(map, key))
                .isEqualTo(key.hashCode() & (map.capacity - 1));
    }

    @Test
    void removeNode() {
        CustomHashMap<Integer, Integer> map = new CustomHashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(null, 4);

        int prevSize = map.size;
        MapUtil.removeNode(map, null);

        assertThat(map.size).isEqualTo(prevSize - 1);
        assertThat(map.containsKey(null)).isFalse();
    }
}