package lma.service;

import lma.constants.CommonConstants;
import lma.dao.CrudDao;
import lma.dao.Dao;
import lma.entity.BaseEntity;

import java.util.List;

import static lma.constants.CommonConstants.AVAILABLE_ENTITIES;
import static lma.constants.ExceptionConstants.NO_SUCH_ENTITY_EXCEPTION_MESSAGE;

public class CrudService<K, E extends BaseEntity> implements Service<K, E> {

    private Dao<K, E> dao;

    public CrudService(Class<E> entityClass) {
        dao = new CrudDao<>(entityClass, AVAILABLE_ENTITIES);
    }

    @Override
    public boolean isPresent(K id) {
        return dao.isPresent(id);
    }

    @Override
    public E findById(K id) {
        return dao.findById(id);
    }

    @Override
    public List<E> findAll() {
        return dao.findAll();
    }

    @Override
    public E save(E entity) {
        dao.save(entity);
        return entity;
    }

    @Override
    public void update(E entity) {
        findById((K) entity.getId());
        dao.update(entity);
    }

    @Override
    public void delete(E entity) {
        findById((K) entity.getId());
        dao.deleteById((K) entity.getId());
    }
}
