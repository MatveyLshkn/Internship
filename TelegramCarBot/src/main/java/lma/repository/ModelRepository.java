package lma.repository;

import lma.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Query("SELECT DISTINCT u.models FROM User u")
    List<Model> findAllSubscribed();

    List<Model> findAllByBrand_Id(Long brandId);

    Model findById(Long id);

    @Query("SELECT u.models FROM User u WHERE u.id = :subscriberId")
    List<Model> findAllModelsBySubscriber(@Param("subscriberId") Long subscriberId);

    @Query("SELECT m.id FROM Model m WHERE m.brand.id = :brandId")
    List<Long> findAllModelIdsByBrandId(@Param("brandId") Long brandId);

}
