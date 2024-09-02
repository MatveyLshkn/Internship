package lma.mapper;

import lma.dto.BrandReadDto;
import lma.dto.ModelReadDto;
import lma.entity.Brand;
import lma.entity.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelMapper {

    private final BrandMapper brandMapper;

    public Model map(ModelReadDto modelReadDto, BrandReadDto brandReadDto) {
        return Model.builder()
                .brand(brandMapper.map(brandReadDto))
                .name(modelReadDto.name())
                .slug(modelReadDto.slug())
                .build();
    }

}
