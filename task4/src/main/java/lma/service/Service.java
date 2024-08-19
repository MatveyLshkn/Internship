package lma.service;

import java.util.List;

public interface Service<K, E> {

    boolean isPresent(K key);

    E findById(K id);

    List<E> findAll();

    E save(E entity);

    void update(E entity);

    void delete(E entity);
}
