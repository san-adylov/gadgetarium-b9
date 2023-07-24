package peaksoft.house.gadgetariumb9.dto.request.banner;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest{
    List<String> bannerImages;
}
