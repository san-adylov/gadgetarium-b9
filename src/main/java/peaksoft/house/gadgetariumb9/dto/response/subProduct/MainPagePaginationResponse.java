package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
    @Setter
    @NoArgsConstructor

    public class MainPagePaginationResponse {

        private List<SubProductMainPageResponse> subProductMainPageResponses;
        private int page;
        private int pageSize;

        @Builder
        public MainPagePaginationResponse(List<SubProductMainPageResponse> subProductMainPageResponses, int page, int pageSize) {
            this.subProductMainPageResponses = subProductMainPageResponses;
            this.page = page;
            this.pageSize = pageSize;
        }
    }


