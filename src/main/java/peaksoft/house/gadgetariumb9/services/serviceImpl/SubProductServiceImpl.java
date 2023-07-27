package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.services.SubProductHistory;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService, SubProductHistory {

  private final SubProductTemplate subProductTemplate;
  private final JwtService jwtService;

  @Override
  public SubProductPagination getSubProductCatalogs(
      SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber) {
    log.info("Filter sub product");
    return subProductTemplate.getProductFilter(subProductCatalogRequest, pageSize, pageNumber);
  }

    @Override
    public InfographicsResponse infographics(String period) {
        return null;
    }

    @Override
  public void addRecentlyViewedProduct(Long productId) {
    User user = jwtService.getAuthenticationUser();
    user.getRecentlyViewedProducts().add(productId);
    log.info("Product added recently viewed");
  }

  @Override
  public List<SubProductHistoryResponse> getRecentlyViewedProduct() {
    log.info("Get recently viewed products");
    return subProductTemplate.getRecentlyViewedProducts();
  }
}

