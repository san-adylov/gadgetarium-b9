package peaksoft.house.gadgetariumb9.services;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;


public interface SubProductService {

  List<SubProductCatalogResponse> getSubProductCatalogs(SubProductCatalogRequest subProductCatalogRequest);

}
