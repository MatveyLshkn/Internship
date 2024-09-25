package lma.repository;

import jakarta.persistence.LockModeType;
import lma.entity.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Query("SELECT DISTINCT m FROM Model m WHERE m IN (SELECT u.models FROM User u)")
    Page<Model> findAllSubscribedModels(Pageable pageable);

    List<Model> findAllByBrand_Id(Long brandId);

    Model findById(Long id);

    @Query("SELECT u.models FROM User u WHERE u.id = :subscriberId")
    List<Model> findAllModelsBySubscriberId(@Param("subscriberId") Long subscriberId);

    @Query("SELECT m.id FROM Model m WHERE m.brand.id = :brandId")
    List<Long> findAllModelIdsByBrandId(@Param("brandId") Long brandId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Model save(Model model);
}