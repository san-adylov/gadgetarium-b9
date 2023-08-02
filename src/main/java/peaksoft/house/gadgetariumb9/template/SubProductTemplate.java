package peaksoft.house.gadgetariumb9.template;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.MainPagePaginationResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;

public interface SubProductTemplate {

  SubProductPagination getProductFilter(SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber);

  List<SubProductHistoryResponse> getRecentlyViewedProducts ();

  InfographicsResponse infographics(String period);

  InfographicsResponse infographics(String period);

  MainPagePaginationResponse getNewProducts(int page, int pageSize);

  MainPagePaginationResponse getRecommendedProducts(int page, int pageSize);

  MainPagePaginationResponse getAllDiscountProducts(int page, int pageSize);
  
}
