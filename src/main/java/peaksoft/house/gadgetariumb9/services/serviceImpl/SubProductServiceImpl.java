package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductPaginationCatalogAdminResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

    private final SubProductTemplate subProductTemplate;

    private final SubProductRepository subProductRepository;

    @Override
    public SubProductPagination getSubProductCatalogs(
            SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber) {
        log.info("Filter sub product");
        return subProductTemplate.getProductFilter(subProductCatalogRequest, pageSize, pageNumber);
    }

    @Override
    public SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productType, int pageSize, int pageNumber) {
        return subProductTemplate.getGetAllSubProductAdmin(productType, pageSize, pageNumber);
    }

    @Override
    public SimpleResponse deleteSubProduct(Long subProductId) {

        if (!subProductRepository.existsById(subProductId)) {
            throw new NotFoundException("SubProduct with id: " + subProductId + "is not found");
        }
        subProductRepository.deleteById(subProductId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubProduct with id: %d is deleted", subProductId))
                .build();
    }
}

