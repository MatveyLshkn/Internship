package lma.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "pageAvByClient", url = "https://cars.av.by/")
public interface PageAvByClient {

    @GetMapping("/filter?brands[0][brand]={brandId}&brands[0][model]={modelId}&page={page}&sort=4")
    String getCarPageSortedAsc(
            @PathVariable Long brandId,
            @PathVariable Long modelId,
            @PathVariable Long page
    );
}