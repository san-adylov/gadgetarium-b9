package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;

public interface DiscountService {
  SimpleResponse saveDiscount(DiscountRequest discountRequest);

}
