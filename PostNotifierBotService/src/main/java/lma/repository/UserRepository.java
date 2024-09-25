package lma.repository;

import jakarta.persistence.LockModeType;
import lma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u JOIN u.models m WHERE m.id = :modelId")
    List<User> findAllBySubscribedModelId(@Param("modelId") Long modelId);
}