package peaksoft.house.gadgetariumb9.dto.request.brand;

import lombok.Builder;

@Builder
public record BrandRequest(String name,

                           String image) {

}

