package lma.CustomArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ListUtilTest {
    private CustomArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    void toArray() {
        Object[] array = ListUtil.toArray(list);

        assertThat(array.length).isEqualTo(list.size);
        for (int i = 0; i < array.length; i++) {
            assertThat(array[i]).isEqualTo(list.get(i));
        }
    }

    @Test
    void trimToSize() {
        int prevCapacity = list.capacity;

        ListUtil.trimToSize(list);
        Object[] elems = list.elements;

        assertThat(list.capacity).isNotEqualTo(prevCapacity);
        assertThat(list.capacity).isEqualTo(list.size);
        assertThat(list.capacity).isEqualTo(elems.length);
    }

    @Test
    void grow() {
        int prevCapacity = list.capacity;
        ListUtil.grow(list);

        assertThat(list.capacity).isNotEqualTo(prevCapacity);
        assertThat(list.capacity).isEqualTo((prevCapacity * 3) / 2 + 1);
    }

    @Test
    void ensureCapacity() {
        int prevCapacity = list.capacity;
        ListUtil.ensureCapacity(list, prevCapacity + 10);

        assertThat(list.capacity).isNotEqualTo(prevCapacity);
        assertThat(list.capacity).isEqualTo(prevCapacity + 10);
    }

    @Test
    void shiftElements() {
        ListUtil.shiftElements(list, 2, 1);

        assertThat(list.get(1)).isEqualTo(3);
    }

    @Test
    void moveAll() {
        CustomArrayList<Integer> list1 = new CustomArrayList<>();
        list1.add(7);

        int prevSize = list1.size;
        ListUtil.moveAll(list, list1);

        assertThat(list1.size).isEqualTo(list.size + prevSize);
        assertThat(list1.contains(list.get(1))).isTrue();
    }
}