package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FeignClientConstants {

    public static final String API_AV_BY_CLIENT_NAME = "apiAvByClient";

    public static final String API_AV_BY_BASE_URL = "https://api.av.by/";

    public static final String API_AV_BY_GET_BRANDS_URL = "/offer-types/cars/catalog/brand-items";

    public static final String API_AV_BY_GET_MODELS_URL = "/offer-types/cars/catalog/brand-items/{brandId}/models";

    public static final String API_AV_BY_GET_POST_INFO_URL = "/offers/{postId}";

    public static final String PAGE_AV_BY_CLIENT_NAME = "pageAvByClient";

    public static final String PAGE_AV_BY_BASE_URL = "https://cars.av.by/";

    public static final String PAGE_AV_BY_GET_CAR_PAGE_SORTED_ASC_URL =
            "/filter?brands[0][brand]={brandId}&brands[0][model]={modelId}&page={page}&sort=4";
}
