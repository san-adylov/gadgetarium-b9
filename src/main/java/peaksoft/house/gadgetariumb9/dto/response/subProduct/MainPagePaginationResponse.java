package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder    
@AllArgsConstructor    
public class MainPagePaginationResponse {

    private List<SubProductMainPageResponse> subProductMainPageResponses;
    private int page;
    private int pageSize;
}
