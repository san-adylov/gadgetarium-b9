package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.response.product.AllInformationProduct;
import peaksoft.house.gadgetariumb9.dto.response.product.ProductUserAndAdminResponse;

public interface ProductTemplate {

  ProductUserAndAdminResponse getByProductId (Long productId, String color);

  AllInformationProduct getAllProductInformation(Long subProductId);
}
