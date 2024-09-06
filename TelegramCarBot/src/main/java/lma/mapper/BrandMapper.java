package lma.mapper;

import lma.dto.BrandDto;
import lma.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand map(BrandDto brandDto) {
        return Brand.builder()
                .id(brandDto.id())
                .name(brandDto.name())
                .slug(brandDto.slug())
                .build();
    }
}
