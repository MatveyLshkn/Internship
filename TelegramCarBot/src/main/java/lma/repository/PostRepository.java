package lma.repository;

import jakarta.persistence.LockModeType;
import lma.entity.Model;
import lma.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByModel_Id(Long modelId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Post save(Post post);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void delete(Post post);

    Page<Post> findAll(Pageable pageable);
}
