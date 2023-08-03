package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubProductPaginationCatalogAdminResponse {

    private int pageSize;

    private int pageNumber;

    private int quantity;

    private List<SubProductCatalogAdminResponse> responseList;

}
