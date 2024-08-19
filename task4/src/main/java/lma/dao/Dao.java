package lma.dao;

import java.util.List;

public interface Dao<K, E>{

    boolean isPresent(K id);

    E findById(K id);

    void deleteById(K id);

    void update(E entity);

    void save(E entity);

    List<E> findAll();
}
