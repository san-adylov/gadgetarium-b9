package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Setter
public class SubProductCatalogAdminResponse {

    private Long subProductId;

    private String images;

    private String productNameAndBrandName;

    private Long articleNumber;

    private LocalDate dateOfCreation;

    private int quantity;

    private String price_and_sale;

    private BigDecimal total_with_discount;

    private double rating;
    @Builder

    public SubProductCatalogAdminResponse(Long subProductId, String images, String productNameAndBrandName, Long articleNumber, LocalDate dateOfCreation, int quantity, String price_and_sale, BigDecimal total_with_discount, double rating) {
        this.subProductId = subProductId;
        this.images = images;
        this.productNameAndBrandName = productNameAndBrandName;
        this.articleNumber = articleNumber;
        this.dateOfCreation = dateOfCreation;
        this.quantity = quantity;
        this.price_and_sale = price_and_sale;
        this.total_with_discount = total_with_discount;
        this.rating = rating;
    }
}

