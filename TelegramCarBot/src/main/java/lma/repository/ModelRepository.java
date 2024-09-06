package lma.repository;

import lma.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    List<Model> findAllByBrand_Id(Long brandId);

    Model findById(Long id);

    @Query("SELECT u.models FROM User u WHERE u.id = :subscriberId")
    List<Model> findAllModelsBySubscriber(@Param("subscriberId") Long subscriberId);

    @Query("SELECT Model.id FROM Model WHERE Model.brand.id = :brandId")
    List<Long> findAllModelIdsByBrandId(@Param("brandId") Long brandId);

}
