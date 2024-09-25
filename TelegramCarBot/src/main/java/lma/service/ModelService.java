package lma.service;

import lma.entity.Model;
import lma.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModelService {

    private final ModelRepository modelRepository;

    public Model findById(Long id){
        return modelRepository.findById(id);
    }

    public List<Model> findAllModelsBySubscriberId(Long id){
        return modelRepository.findAllModelsBySubscriberId(id);
    }

    public List<Model> findAllByBrandId(Long id){
        return modelRepository.findAllByBrand_Id(id);
    }

    public List<Long> findAllModelsIdsByBrandId(Long id){
        return modelRepository.findAllModelIdsByBrandId(id);
    }

    @Transactional
    public Model save(Model model){
        return modelRepository.save(model);
    }

    public Page<Model> findAllSubscribedModels(Pageable pageable){
        return modelRepository.findAllSubscribedModels(pageable);
    }
}
