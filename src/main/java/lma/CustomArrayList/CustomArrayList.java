package lma.CustomArrayList;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomArrayList<T> {
    private final int DEFAULT_CAPACITY = 10;

    T[] elements;

    int capacity;

    int size;

    public CustomArrayList() {
        elements = (T[]) new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    public CustomArrayList(int capacity) {
        elements = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public CustomArrayList(T[] elements) {
        capacity = size = elements.length;
        this.elements = Arrays.copyOf(elements, elements.length);
    }

    public CustomArrayList(CustomArrayList<T> list) {
        this(ListUtil.toArray(list));
    }

    public void add(T element) {
        if (size == capacity) {
            ListUtil.grow(this);
        }
        elements[size++] = element;
    }

    public void add(int index, T element) {
        ListUtil.checkIndex(this, index);
        if (size == capacity) {
            ListUtil.grow(this);
        }
        ListUtil.shiftElements(this, index, index + 1);
        elements[index] = element;
        size++;
    }

    public void addAll(CustomArrayList<T> list) {
        ListUtil.ensureCapacity(this, size + list.size);
        ListUtil.moveAll(list, this);
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) return i;
        }
        return -1;
    }

    public T delete(int index) {
        ListUtil.checkIndex(this, index);
        T element = elements[index];
        ListUtil.shiftElements(this, index + 1, index);
        size--;
        return element;
    }

    public T remove(T element) {
        if (!contains(element)) throw new NoSuchElementException();
        int index = indexOf(element);
        return delete(index);
    }

    public T get(int index) {
        ListUtil.checkIndex(this, index);
        return elements[index];
    }

    public T set(int index, T element) {
        ListUtil.checkIndex(this, index);
        T oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    public boolean contains(T element) {
        return indexOf(element) > -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(ListUtil.toArray(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return capacity == that.capacity && size == that.size && Objects.deepEquals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(elements), capacity, size);
    }
}