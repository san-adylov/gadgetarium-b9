package peaksoft.house.gadgetariumb9.dto.response.brand;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class BrandResponse {
    
    private Long id;

    private String name;

    private String image;

    public BrandResponse(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
