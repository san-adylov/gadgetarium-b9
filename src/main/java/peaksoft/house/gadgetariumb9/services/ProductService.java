package peaksoft.house.gadgetariumb9.services;


import peaksoft.house.gadgetariumb9.dto.request.product.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

import java.util.List;

public interface ProductService {

    SimpleResponse saveProduct(ProductRequest productRequest);

    public List<String> getColor(String name);
}
