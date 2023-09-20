package peaksoft.house.gadgetariumb9.services;

import java.util.Map;
import peaksoft.house.gadgetariumb9.dto.request.product.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.response.product.AllInformationProduct;
import peaksoft.house.gadgetariumb9.dto.response.product.ProductUserAndAdminResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import java.util.List;

public interface ProductService {

    SimpleResponse saveProduct(ProductRequest productRequest);

    List<String> getColor(String name);

    ProductUserAndAdminResponse getByProductId(Long productId, String color);

    Map<String, String> getColorNames(List<String> codes);

    AllInformationProduct getAllProductInformation(Long subProductId);

}
