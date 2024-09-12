package lma.service;

import lma.entity.ModelCheck;
import lma.repository.ModelCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ModelCheckService {

    private final ModelCheckRepository modelCheckRepository;

    public ModelCheck findByModelId(Long id){
        return modelCheckRepository.findByModelId(id);
    }

    public ModelCheck save(ModelCheck modelCheck){
        return modelCheckRepository.save(modelCheck);
    }
}
