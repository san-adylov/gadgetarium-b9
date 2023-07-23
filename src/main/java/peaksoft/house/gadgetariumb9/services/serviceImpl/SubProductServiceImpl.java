package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

  private final SubProductTemplate subProductTemplate;

  @Override
  public List<SubProductCatalogResponse> getSubProductCatalogs(
      SubProductCatalogRequest subProductCatalogRequest) {
    return subProductTemplate.getProductFilter(subProductCatalogRequest);
  }
}

