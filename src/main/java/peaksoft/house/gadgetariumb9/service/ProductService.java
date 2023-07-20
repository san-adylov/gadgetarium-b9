package peaksoft.house.gadgetariumb9.service;


import peaksoft.house.gadgetariumb9.dto.request.productRequest.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

import java.util.List;

public interface ProductService {

    SimpleResponse saveProduct(ProductRequest productRequest);

    public List<String> getColor(String name);
}
