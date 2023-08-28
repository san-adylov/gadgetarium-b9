package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SubProductMainPageResponse {

    private Long subProductId;

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

    private boolean isFavorite;

}


