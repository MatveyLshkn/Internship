package lma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

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

    private void grow() {
        capacity *= 2;
        elements = Arrays.copyOf(elements, capacity);
    }

    public void add(T element) {
        if (size == capacity) {
            grow();
        }
        elements[size++] = element;
    }

    public int firstIndexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) return i;
        }
        return -1;
    }

    public T delete(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        T element = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        return element;
    }

    public T remove(T element) {
        if (!contains(element)) throw new NoSuchElementException();
        int index = firstIndexOf(element);
        return delete(index);
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return elements[index];
    }

    public boolean contains(T element) {
        return firstIndexOf(element) > -1;
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

    public T[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public void addAll(T[] elements) {
        if (elements.length > capacity - size) {
            ensureCapacity(capacity + elements.length);
        }
        System.arraycopy(elements, 0, this.elements, size, elements.length);
    }

    public void addAll(CustomArrayList<T> list) {
        addAll(list.toArray());
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
}
