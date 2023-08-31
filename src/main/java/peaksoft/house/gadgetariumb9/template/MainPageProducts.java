package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.response.subProduct.MainPagePaginationResponse;

public interface MainPageProducts {

    MainPagePaginationResponse getNewProducts(int page, int pageSize);

    MainPagePaginationResponse getRecommendedProducts(int page, int pageSize);

    MainPagePaginationResponse getAllDiscountProducts(int page, int pageSize);
}
