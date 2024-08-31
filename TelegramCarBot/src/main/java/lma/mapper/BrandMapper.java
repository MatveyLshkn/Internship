package lma.mapper;

import lma.dto.BrandReadDto;
import lma.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand map(BrandReadDto brandReadDto) {
        return Brand.builder()
                .id(brandReadDto.id())
                .name(brandReadDto.name())
                .slug(brandReadDto.slug())
                .build();
    }
}
