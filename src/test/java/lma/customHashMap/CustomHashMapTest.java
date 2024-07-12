package lma.customHashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomHashMapTest {
    private CustomHashMap<Integer, Integer> customHashMap;

    @BeforeEach
    void setUp() {
        customHashMap = new CustomHashMap<>();
        customHashMap.put(1, 1);
        customHashMap.put(2, 2);
        customHashMap.put(3, 3);
        customHashMap.put(null, 4);
    }

    @Test
    void put() {
        assertThat(customHashMap.size).isEqualTo(4);
    }

    @Test
    void get() {
        assertThat(customHashMap.get(2)).isEqualTo(2);
        assertThat(customHashMap.get(null)).isEqualTo(4);
    }

    @Test
    void containsKey() {
        assertThat(customHashMap.containsKey(3)).isTrue();
        assertThat(customHashMap.containsKey(4)).isFalse();
        assertThat(customHashMap.containsKey(null)).isTrue();
    }

    @Test
    void containsValue() {
        assertThat(customHashMap.containsValue(4)).isTrue();
        assertThat(customHashMap.containsValue(111)).isFalse();
    }

    @Test
    void replace() {
        customHashMap.replace(2, 99);
        assertThat(customHashMap.get(2)).isEqualTo(99);
    }

    @Test
    void remove() {
        int prevSize = customHashMap.size;

        customHashMap.remove(2);

        assertThat(customHashMap.containsKey(2)).isFalse();
        assertThat(customHashMap.size).isEqualTo(prevSize - 1);
    }
}