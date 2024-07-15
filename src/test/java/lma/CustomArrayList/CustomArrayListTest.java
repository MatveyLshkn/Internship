package lma.CustomArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomArrayListTest {
    private CustomArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    void add() {
        int prevSize = list.size();

        list.add(4);

        assertThat(list.size).isEqualTo(prevSize + 1);
    }

    @Test
    void addAll() {
        CustomArrayList<Integer> list2 = new CustomArrayList<>();

        list2.addAll(list);

        assertThat(list2.size).isEqualTo(3);
    }

    @Test
    void indexOf() {
        int index = list.indexOf(2);

        assertThat(index).isEqualTo(1);
    }

    @Test
    void delete() {
        int deletedElem = list.delete(1);

        assertThat(list.size).isEqualTo(2);
        assertThat(deletedElem).isEqualTo(2);
    }

    @Test
    void remove() {
        int deletedElem = list.remove(3);

        assertThat(list.size).isEqualTo(2);
        assertThat(deletedElem).isEqualTo(3);
        assertThat(list.indexOf(3)).isEqualTo(-1);
    }

    @Test
    void get() {
        Integer actualElem = list.get(2);

        assertThat(actualElem).isEqualTo(3);
    }

    @Test
    void set() {
        list.set(2, 4);

        assertThat(list.get(2)).isEqualTo(4);
    }

    @Test
    void contains() {
        assertThat(list.contains(3)).isTrue();
    }
}