package peaksoft.house.gadgetariumb9.service;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;


public interface SubProductService {

  List<SubProductCatalogResponse> getSubProductCatalogs ();

}
