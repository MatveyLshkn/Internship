package lma.customHashMap;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class MapUtilTest {

    @Test
    void resize() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("Joe", 1);
        map.put("Rick", 2);
        map.put("Anton", 3);
        map.put(null, 4);

        int prevCapacity = map.capacity;
        MapUtil.resize(map);

        assertThat(map.capacity).isEqualTo(prevCapacity * 2);
        assertThat(map.containsKey(null)).isTrue();
        assertThat(map.containsKey("Alexander")).isFalse();
    }

    @Test
    void addNode() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        int prevSize = map.size;

        MapUtil.addNode(map, null, 4, 0);
        MapUtil.addNode(map, "Elizabeth", 66, 0);

        assertThat(map.size).isEqualTo(prevSize + 2);
        assertThat(map.containsKey(null)).isTrue();
        assertThat(map.containsKey("Elizabeth")).isTrue();
    }

    @Test
    void calculatePos() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        String key = "Test";

        assertThat(MapUtil.calculatePos(map, null)).isEqualTo(0);
        assertThat(MapUtil.calculatePos(map, key))
                .isEqualTo(key.hashCode() & (map.capacity - 1));
    }

    @Test
    void removeNode() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("Joe", 1);
        map.put("Rick", 2);
        map.put("Anton", 3);
        map.put(null, 4);

        int prevSize = map.size;
        MapUtil.removeNode(map, null);

        assertThat(map.size).isEqualTo(prevSize - 1);
        assertThat(map.containsKey(null)).isFalse();
    }
}