package lma.repository;

import lma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.models WHERE u.id = :userId")
    public User findByIdWithModelsInitialized(@Param("userId") Long userId);

    public User findById(Long id);

    @Query("SELECT u FROM User u JOIN u.models m WHERE m.id = :modelId")
    public List<User> findAllBySubscribedModelId(@Param("modelId") Long modelId);
}
