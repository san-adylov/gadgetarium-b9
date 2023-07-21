package peaksoft.house.gadgetariumb9.template;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.PhoneCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;

public interface SubProductTemplate {

  List<SubProductCatalogResponse> getPhones(PhoneCatalogRequest phoneCatalogRequest);

}
