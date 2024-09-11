package lma.repository;

import jakarta.persistence.LockModeType;
import lma.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT DISTINCT u.models FROM User u")
    List<Model> findAllSubscribed();

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Model> findAllByBrand_Id(Long brandId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Model findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT u.models FROM User u WHERE u.id = :subscriberId")
    List<Model> findAllModelsBySubscriberId(@Param("subscriberId") Long subscriberId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT m.id FROM Model m WHERE m.brand.id = :brandId")
    List<Long> findAllModelIdsByBrandId(@Param("brandId") Long brandId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Model save(Model model);
}