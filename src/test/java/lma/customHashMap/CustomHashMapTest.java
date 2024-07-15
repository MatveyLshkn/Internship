package lma.customHashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomHashMapTest {
    private CustomHashMap<String, Integer> customHashMap;

    @BeforeEach
    void setUp() {
        customHashMap = new CustomHashMap<>();
        customHashMap.put("Ivan", 1);
        customHashMap.put("Matthew", 2);
        customHashMap.put("Andrew", 3);
        customHashMap.put(null, 4);
    }

    @Test
    void put() {
        assertThat(customHashMap.size).isEqualTo(4);
    }

    @Test
    void get() {
        assertThat(customHashMap.get("Matthew")).isEqualTo(2);
        assertThat(customHashMap.get(null)).isEqualTo(4);
    }

    @Test
    void containsKey() {
        assertThat(customHashMap.containsKey("Andrew")).isTrue();
        assertThat(customHashMap.containsKey("Test")).isFalse();
        assertThat(customHashMap.containsKey(null)).isTrue();
    }

    @Test
    void containsValue() {
        assertThat(customHashMap.containsValue(4)).isTrue();
        assertThat(customHashMap.containsValue(-1)).isFalse();
    }

    @Test
    void replace() {
        customHashMap.replace("Ivan", 99);
        assertThat(customHashMap.get("Ivan")).isEqualTo(99);
    }

    @Test
    void remove() {
        int prevSize = customHashMap.size;

        customHashMap.remove("Matthew");

        assertThat(customHashMap.containsKey("Matthew")).isFalse();
        assertThat(customHashMap.size).isEqualTo(prevSize - 1);
    }

    @Test
    void checkException() {
        try {
            customHashMap.remove("John");
            fail();
        } catch (NoSuchKeyException exception) {
            exception.printStackTrace();
        }
    }
}