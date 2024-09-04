package lma.client;

import lma.constants.FeignClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static lma.constants.FeignClientConstants.PAGE_AV_BY_BASE_URL;
import static lma.constants.FeignClientConstants.PAGE_AV_BY_CLIENT_NAME;
import static lma.constants.FeignClientConstants.PAGE_AV_BY_GET_CAR_PAGE_SORTED_ASC_URL;

@Component
@FeignClient(value = PAGE_AV_BY_CLIENT_NAME, url = PAGE_AV_BY_BASE_URL)
public interface PageAvByClient {

    @GetMapping(PAGE_AV_BY_GET_CAR_PAGE_SORTED_ASC_URL)
    String getCarPageSortedAsc(
            @PathVariable Long brandId,
            @PathVariable Long modelId,
            @PathVariable Long page
    );
}