package peaksoft.house.gadgetariumb9.dto.request.brand;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record BrandRequest(String name,

                           MultipartFile image) {

}

