package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;
import peaksoft.house.gadgetariumb9.dto.response.simple.SimpleResponse;

public interface DiscountService {

  SimpleResponse saveDiscount(DiscountRequest discountRequest);

}
