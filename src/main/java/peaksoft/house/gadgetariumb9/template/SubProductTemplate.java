package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;

public interface SubProductTemplate {

  SubProductPagination getProductFilter(SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber);

  InfographicsResponse infographics(String period);
}
