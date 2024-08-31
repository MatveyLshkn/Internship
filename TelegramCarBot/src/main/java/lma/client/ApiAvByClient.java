package lma.client;

import lma.dto.BrandReadDto;
import lma.dto.ModelReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(value = "avByClient", url = "https://api.av.by/")
public interface ApiAvByClient {

    @GetMapping("/offer-types/cars/catalog/brand-items")
    List<BrandReadDto> getBrands();

    @GetMapping("/offer-types/cars/catalog/brand-items/{brandId}/models")
    List<ModelReadDto> getModels(@PathVariable Long brandId);
}