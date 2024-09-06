package lma.mapper;

import lma.dto.BrandDto;
import lma.dto.ModelDto;
import lma.entity.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelMapper {

    private final BrandMapper brandMapper;

    public Model map(ModelDto modelDto, BrandDto brandDto) {
        return Model.builder()
                .brand(brandMapper.map(brandDto))
                .name(modelDto.name())
                .slug(modelDto.slug())
                .build();
    }

}
