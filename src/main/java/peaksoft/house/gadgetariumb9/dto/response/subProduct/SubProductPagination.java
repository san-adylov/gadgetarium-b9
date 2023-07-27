package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class SubProductPagination {

    private List<SubProductCatalogResponse> responseList;

    private int pageSize;

    private int numberPage;

    @Builder
    public SubProductPagination(List<SubProductCatalogResponse> responseList, int pageSize,
                                int numberPage) {
        this.responseList = responseList;
        this.pageSize = pageSize;
        this.numberPage = numberPage;
    }
}
