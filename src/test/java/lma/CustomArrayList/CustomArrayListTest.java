package lma.CustomArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomArrayListTest {

    @Test
    void add() {
        CustomArrayList<Integer> list = new CustomArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.size).isEqualTo(3);
    }

    @Test
    void addAll() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        CustomArrayList<Integer> list2 = new CustomArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);

        list.addAll(list2);

        assertThat(list.size).isEqualTo(3);
    }

    @Test
    void indexOf() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int index = list.indexOf(2);

        assertThat(index).isEqualTo(1);
    }

    @Test
    void delete() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int deletedElem = list.delete(1);

        assertThat(list.size).isEqualTo(2);
        assertThat(deletedElem).isEqualTo(2);
    }

    @Test
    void remove() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int deletedElem = list.remove(3);

        assertThat(list.size).isEqualTo(2);
        assertThat(deletedElem).isEqualTo(3);
        assertThat(list.indexOf(3)).isEqualTo(-1);
    }

    @Test
    void get() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Integer actualElem = list.get(2);

        assertThat(actualElem).isEqualTo(3);
    }

    @Test
    void set() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.set(2, 4);

        assertThat(list.get(2)).isEqualTo(4);
    }

    @Test
    void contains() {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(list.contains(3)).isTrue();
    }
}
