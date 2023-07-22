package peaksoft.house.gadgetariumb9.api;

import jakarta.annotation.security.PermitAll;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;
import peaksoft.house.gadgetariumb9.service.SubProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subProduct")
public class SubProductApi {

  private final SubProductService subProductService;

  @PostMapping("/filter")
  @PreAuthorize("hasAnyAuthority(permitAll())")
  @PermitAll
  public List<SubProductCatalogResponse> filter (@RequestBody SubProductCatalogRequest subProductCatalogRequest){
    return subProductService.getSubProductCatalogs(subProductCatalogRequest);
  }

}
