package lma.repository;

import lma.entity.ModelCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelCheckRepository extends JpaRepository<ModelCheck, Integer> {

    ModelCheck findByModelId(Long modelId);
}
