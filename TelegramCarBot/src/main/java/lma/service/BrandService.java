package lma.service;

import lma.entity.Brand;
import lma.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand findById(Long id){
        return brandRepository.findById(id);
    }

    public List<Brand> findAll(){
        return brandRepository.findAll();
    }

    public List<Long> findAllBrandsIds(){
        return brandRepository.findAllBrandsIds();
    }

    @Transactional
    public Brand save(Brand brand){
        return brandRepository.save(brand);
    }
}
