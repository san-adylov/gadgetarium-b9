package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

  private final SubProductTemplate subProductTemplate;

  @Override
  public SubProductPagination getSubProductCatalogs(
      SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber) {
    return subProductTemplate.getProductFilter(subProductCatalogRequest, pageSize, pageNumber);
  }
}

