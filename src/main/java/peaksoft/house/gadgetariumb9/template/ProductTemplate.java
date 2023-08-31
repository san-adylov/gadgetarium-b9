package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.response.product.ProductUserAndAdminResponse;

public interface ProductTemplate {

  ProductUserAndAdminResponse getByProductId (Long subProductId, String color);
}
