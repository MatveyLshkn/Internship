package lma.repository;

import jakarta.persistence.LockModeType;
import lma.entity.ModelCheck;
import org.apache.kafka.common.network.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ModelCheckRepository extends JpaRepository<ModelCheck, Integer> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    ModelCheck findByModelId(Long modelId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ModelCheck save(ModelCheck modelCheck);
}
