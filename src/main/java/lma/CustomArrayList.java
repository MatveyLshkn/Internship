package lma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private T[] elements;

    private int capacity;

    private int size;

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
        this(list.toArray());
    }

    private boolean checkIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        return true;
    }

    private void grow() {
        capacity = (capacity * 3) / 2 + 1;
        elements = Arrays.copyOf(elements, capacity);
    }

    public void ensureCapacity(int capacity) {
        if (this.capacity < capacity) {
            this.capacity = capacity;
            elements = Arrays.copyOf(elements, capacity);
        }
    }

    public void trimToSize() {
        elements = toArray();
    }

    public void add(T element) {
        if (size == capacity) {
            grow();
        }
        elements[size++] = element;
    }

    public void add(int index, T element) {
        checkIndex(index);
        if (size == capacity) {
            grow();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    public void addAll(T[] elements) {
        if (elements.length > capacity - size) {
            ensureCapacity(capacity + elements.length);
        }
        System.arraycopy(elements, 0, this.elements, size, elements.length);
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) return i;
        }
        return -1;
    }

    public T delete(int index) {
        checkIndex(index);
        T element = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        return element;
    }

    public T remove(T element) {
        if (!contains(element)) throw new NoSuchElementException();
        int index = indexOf(element);
        return delete(index);
    }

    public T get(int index) {
        checkIndex(index);
        return elements[index];
    }

    public T set(int index, T element) {
        checkIndex(index);
        T oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    public boolean contains(T element) {
        return indexOf(element) > -1;
    }

    public T[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
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