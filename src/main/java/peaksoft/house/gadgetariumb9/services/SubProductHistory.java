package peaksoft.house.gadgetariumb9.services;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductHistoryResponse;

public interface SubProductHistory {

  void addRecentlyViewedProduct (Long productId);

  List<SubProductHistoryResponse> getRecentlyViewedProduct();

}
