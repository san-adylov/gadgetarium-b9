package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductPaginationCatalogAdminResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface SubProductService {

 SubProductPagination getSubProductCatalogs(SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber);

 SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productType, int pageSize, int pageNumber);

 SimpleResponse deleteSubProduct(Long subProductId);

}
