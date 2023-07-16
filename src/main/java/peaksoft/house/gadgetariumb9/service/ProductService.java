package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest.ProductRequest;

public interface ProductService {

    SimpleResponse saveProduct(ProductRequest productRequest);
}
