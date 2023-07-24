package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

  private final SubProductTemplate subProductTemplate;

  @Override
  public SubProductPagination getSubProductCatalogs(
      SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber) {
    log.info("Filter sub product");
    return subProductTemplate.getProductFilter(subProductCatalogRequest, pageSize, pageNumber);
  }

  @Override
  public InfographicsResponse infographics(String period) {
    return subProductTemplate.infographics(period);
  }
}

