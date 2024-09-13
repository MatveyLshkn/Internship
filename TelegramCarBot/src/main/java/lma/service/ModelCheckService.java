package lma.service;

import lma.entity.ModelCheck;
import lma.repository.ModelCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ModelCheckService {

    private final ModelCheckRepository modelCheckRepository;

    @Transactional(readOnly = true)
    public ModelCheck findByModelId(Long id){
        return modelCheckRepository.findByModelId(id);
    }

    public ModelCheck save(ModelCheck modelCheck){
        return modelCheckRepository.save(modelCheck);
    }

    public void updateModelCheckDate(ModelCheck modelCheck, Long modelId) {
        if (modelCheck != null) {
            modelCheck.setCheckDate(Timestamp.valueOf(LocalDateTime.now()));
            save(modelCheck);
        } else {
            save(ModelCheck.builder()
                    .modelId(modelId)
                    .checkDate(Timestamp.valueOf(LocalDateTime.now()))
                    .build());
        }
    }
}
