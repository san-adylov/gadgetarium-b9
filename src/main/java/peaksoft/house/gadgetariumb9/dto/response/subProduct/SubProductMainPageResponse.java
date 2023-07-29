package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SubProductMainPageResponse {
    private long subProductId;
    private String name;
    private String prodName;
    private BigDecimal price;
    private String color;
    private String image;
    private int quantity;
    private double rating;
    private int discount;
    private LocalDate createdAt;
    private String subCatTitle;
    private String catTitle;
    private String grade;
    private int countOfReviews;

}


