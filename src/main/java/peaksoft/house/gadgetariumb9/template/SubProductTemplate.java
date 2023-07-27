package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductPaginationCatalogAdminResponse;

public interface SubProductTemplate {

  SubProductPagination getProductFilter(SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber);

  SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productType, int pageSize, int pageNumber);

}
