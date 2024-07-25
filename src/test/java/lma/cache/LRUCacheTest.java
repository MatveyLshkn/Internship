package lma.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {

    LRUCache<String, String> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(5);
        cache.put("one", "Some test text for one");
        cache.put("two", "Test two text for two");
        cache.put("three", "Third test text for three");
        cache.put("four", "Test four text for four");
        cache.put("five", "Hello world!");
    }

    @Test
    void put() {
        int prevSize = cache.getSize();

        cache.put("testText", "test text");

        assertThat(cache.contains("testText")).isTrue();
    }

    @Test
    void contains() {
        assertThat(cache.contains("one")).isTrue();
        assertThat(cache.contains("notHere")).isFalse();
    }

    @Test
    void get() {
        assertThat(cache.get("one")).isEqualTo("Some test text for one");
    }

    @Test
    void remove() {
        int prevSize = cache.getSize();

        cache.remove("one");

        assertThat(cache.getSize()).isEqualTo(prevSize - 1);
        assertThat(cache.contains("one")).isFalse();
    }

    @Test
    void clear() {
        int prevSize = cache.getSize();

        cache.clear();

        assertThat(cache.getSize()).isNotEqualTo(prevSize);
        assertThat(cache.getSize()).isEqualTo(0);
    }
}