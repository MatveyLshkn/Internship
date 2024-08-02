package lma.dao;

import lma.entity.BaseEntity;
import lma.entity.description.EntityDescription;
import lma.exception.DatabaseException;
import lma.exception.RecordNotFoundException;
import lma.exception.NoSuchEntityException;
import lma.mapper.EntityMapper;
import lma.queryFactory.SqlQueryFactory;
import lma.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lma.constants.ExceptionConstants.DATABASE_EXCEPTION_MESSAGE;
import static lma.constants.ExceptionConstants.RECORD_NOT_FOUND_EXCEPTION_MESSAGE;
import static lma.constants.ExceptionConstants.NO_SUCH_ENTITY_EXCEPTION_MESSAGE;

public class CrudDao<K, E extends BaseEntity<K>> implements Dao<K, E>{

    private SqlQueryFactory queryFactory;

    private EntityMapper<E> entityMapper;

    private EntityDescription<E> entityDescription;

    public CrudDao(Class<E> clazz, List<EntityDescription> entityDescriptions) {
        for (EntityDescription description : entityDescriptions) {
            if(description.getEntityClass().isAssignableFrom(clazz)) {
                queryFactory = new SqlQueryFactory(description.getTableQueryInfo());
                entityMapper = description.getEntityMapper();
                entityDescription = description;
                return;
            }
        }

        throw new NoSuchEntityException(NO_SUCH_ENTITY_EXCEPTION_MESSAGE
                .formatted(clazz.getSimpleName()));
    }

    @Override
    public boolean isPresent(K id) {
        String findQuery = queryFactory.getIsPresentQuery();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean result = false;
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
            return result;

        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE.formatted(e));
        }
    }

    @Override
    public E findById(K id) {
        String findQuery = queryFactory.getFindByIdQuery();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            E entity = null;
            if (resultSet.next()) {
                entity = entityMapper.mapFromResultSet(resultSet);
            }
            return Optional.ofNullable(entity)
                    .orElseThrow(() -> new RecordNotFoundException(RECORD_NOT_FOUND_EXCEPTION_MESSAGE
                            .formatted(id, entityDescription.getTableQueryInfo().getTableName())));

        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE.formatted(e));
        }
    }

    @Override
    public void deleteById(K id) {
        String deleteQuery = queryFactory.getDeleteByIdQuery();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE.formatted(e));
        }
    }

    @Override
    public void update(E entity) {
        String updateQuery = queryFactory.getUpdateByIdQuery();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            entityMapper.mapToPreparedStatement(entity, preparedStatement, true)
                    .executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE.formatted(e));
        }
    }

    @Override
    public void save(E entity) {
        String saveQuery = queryFactory.getSaveQuery();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS)) {

            entityMapper.mapToPreparedStatement(entity, preparedStatement, false)
                    .executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId((K) generatedKeys.getObject(1));
            }

        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE.formatted(e));
        }
    }

    @Override
    public List<E> findAll() {
        String findAllQuery = queryFactory.getFindAllQuery();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<E> entityList = new ArrayList<>();
            while (resultSet.next()) {
                entityList.add(entityMapper.mapFromResultSet(resultSet));
            }
            return entityList;

        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE.formatted(e));
        }
    }
}
