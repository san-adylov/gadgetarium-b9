package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface DiscountService {

    SimpleResponse saveDiscount(DiscountRequest discountRequest);

}
