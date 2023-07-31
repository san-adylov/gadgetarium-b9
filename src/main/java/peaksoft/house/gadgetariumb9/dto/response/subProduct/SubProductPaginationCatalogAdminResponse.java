package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SubProductPaginationCatalogAdminResponse {
    private int pageSize;
    private int pageNumber;
    private int quantity;
    private List<SubProductCatalogAdminResponse> responseList;


    public SubProductPaginationCatalogAdminResponse(int pageSize, int pageNumber, int quantity, List<SubProductCatalogAdminResponse> responseList) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.quantity = quantity;
        this.responseList = responseList;
    }
}
