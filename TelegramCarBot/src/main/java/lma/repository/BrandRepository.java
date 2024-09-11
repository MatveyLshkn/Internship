package lma.repository;

import jakarta.persistence.LockModeType;
import lma.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT b.id FROM Brand b")
    List<Long> findAllBrandsIds();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Brand save(Brand brand);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Brand> findAll();
}