package lma.client;

import feign.Response;
import lma.dto.BrandDto;
import lma.dto.ModelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static lma.constants.FeignClientConstants.API_AV_BY_BASE_URL;
import static lma.constants.FeignClientConstants.API_AV_BY_CLIENT_NAME;
import static lma.constants.FeignClientConstants.API_AV_BY_GET_BRANDS_URL;
import static lma.constants.FeignClientConstants.API_AV_BY_GET_MODELS_URL;
import static lma.constants.FeignClientConstants.API_AV_BY_GET_POST_INFO_URL;

@Component
@FeignClient(value = API_AV_BY_CLIENT_NAME, url = API_AV_BY_BASE_URL)
public interface ApiAvByClient {

    @GetMapping(API_AV_BY_GET_BRANDS_URL)
    List<BrandDto> getBrands();

    @GetMapping(API_AV_BY_GET_MODELS_URL)
    List<ModelDto> getModels(@PathVariable Long brandId);

    @GetMapping(API_AV_BY_GET_POST_INFO_URL)
    Response getPostInfo(@PathVariable Long postId);
}