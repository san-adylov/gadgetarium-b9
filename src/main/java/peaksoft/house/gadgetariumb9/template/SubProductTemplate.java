package peaksoft.house.gadgetariumb9.template;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.MainPagePaginationResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPaginationCatalogAdminResponse;

public interface SubProductTemplate {

  SubProductPagination getProductFilter(SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber);

    InfographicsResponse infographics(String period);

  List<SubProductHistoryResponse> getRecentlyViewedProducts ();

 //   SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productType, LocalDate startDate, LocalDate endDate, int pageSize, int pageNumber);


  MainPagePaginationResponse getNewProducts(int page, int pageSize);

  MainPagePaginationResponse getRecommendedProducts(int page, int pageSize);

  MainPagePaginationResponse getAllDiscountProducts(int page, int pageSize);

  SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productTyp, LocalDateTime startDate, LocalDateTime endDate, int pageSize, int pageNumber);
}
