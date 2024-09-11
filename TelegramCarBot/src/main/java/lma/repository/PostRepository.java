package lma.repository;

import jakarta.persistence.LockModeType;
import lma.entity.Model;
import lma.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Long countPostByModel_Id(Long model_id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Post> findAllByModel_Id(Long modelId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Post> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Post save(Post post);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void delete(Post post);
}
