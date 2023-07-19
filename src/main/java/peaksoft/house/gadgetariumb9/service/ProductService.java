package peaksoft.house.gadgetariumb9.service;


import peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest.ProductRequest;

import java.util.List;

public interface ProductService {

    SimpleResponse saveProduct(ProductRequest productRequest);

    public List<String> getColor();
}
