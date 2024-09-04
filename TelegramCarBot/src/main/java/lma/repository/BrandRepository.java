package lma.repository;

import lma.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("SELECT Brand.id FROM Brand")
    List<Long> findAllBrandIds();
}
