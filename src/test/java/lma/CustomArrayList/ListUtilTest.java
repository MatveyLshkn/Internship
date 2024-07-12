package lma.CustomArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ListUtilTest {

    @Test
    void toArray() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Object[] array = ListUtil.toArray(list);

        assertThat(array.length).isEqualTo(list.size);
        for (int i = 0; i < array.length; i++) {
            assertThat(array[i]).isEqualTo(list.get(i));
        }
    }

    @Test
    void trimToSize() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int prevCapacity = list.capacity;
        ListUtil.trimToSize(list);
        Object[] elems = list.elements;

        assertThat(list.capacity).isNotEqualTo(prevCapacity);
        assertThat(list.capacity).isEqualTo(list.size);
        assertThat(list.capacity).isEqualTo(elems.length);
    }

    @Test
    void grow() {
        CustomArrayList<Integer> list = new CustomArrayList<>();

        int prevCapacity = list.capacity;
        ListUtil.grow(list);

        assertThat(list.capacity).isNotEqualTo(prevCapacity);
        assertThat(list.capacity).isEqualTo((prevCapacity * 3) / 2 + 1);
    }

    @Test
    void ensureCapacity() {
        CustomArrayList<Integer> list = new CustomArrayList<>(3);

        int prevCapacity = list.capacity;
        ListUtil.ensureCapacity(list, 7);

        assertThat(list.capacity).isNotEqualTo(prevCapacity);
        assertThat(list.capacity).isEqualTo(7);
    }

    @Test
    void shiftElements() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        ListUtil.shiftElements(list, 2, 1);

        assertThat(list.get(1)).isEqualTo(3);
    }

    @Test
    void moveAll() {
        CustomArrayList<Integer> list1 = new CustomArrayList<>();
        list1.add(1);
        CustomArrayList<Integer> list2 = new CustomArrayList<>();
        list2.add(2);
        list2.add(3);

        ListUtil.moveAll(list2, list1);

        assertThat(list1.size).isEqualTo(3);
        assertThat(list1.contains(list2.get(1))).isTrue();
    }
}