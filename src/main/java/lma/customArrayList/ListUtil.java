package lma.customArrayList;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class ListUtil {
    <T> T[] toArray(CustomArrayList<T> list) {
        return Arrays.copyOf(list.elements, list.size);
    }

    <T> void trimToSize(CustomArrayList<T> list) {
        list.elements = toArray(list);
        list.capacity = list.elements.length;
    }

    <T> void grow(CustomArrayList<T> list) {
        list.capacity = (list.capacity * 3) / 2 + 1;
        list.elements = Arrays.copyOf(list.elements, list.capacity);
    }

    <T> void ensureCapacity(CustomArrayList<T> list, int capacity) {
        if (list.capacity < capacity) {
            list.capacity = capacity;
            list.elements = Arrays.copyOf(list.elements, capacity);
        }
    }

    <T> void shiftElements(CustomArrayList<T> list, int fromIndex, int toIndex) {
        if (list.capacity == list.size) {
            throw new InsufficientSizeException();
        }
        System.arraycopy(list.elements, fromIndex, list.elements, toIndex, list.size - fromIndex);
    }

    <T> void moveAll(CustomArrayList<T> fromList, CustomArrayList<T> toList) {
        if (toList.capacity - toList.size < fromList.size) {
            throw new InsufficientSizeException();
        }
        System.arraycopy(fromList.elements, 0, toList.elements, toList.size, fromList.size);
        toList.size += fromList.size;
    }

    void checkIndex(CustomArrayList list, int index) {
        if (index < 0 || index >= list.size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
