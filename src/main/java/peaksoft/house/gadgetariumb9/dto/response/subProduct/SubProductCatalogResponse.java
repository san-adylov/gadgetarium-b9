package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class SubProductCatalogResponse {

    private Long subProductId;

    private Long productId;

    private int discount;

    private String image;

    private int quantity;

    private String name;

    private int rating;

    private BigDecimal price;

    private boolean isFavorite;

    private boolean isComparison;

    private boolean isBasket;

    @Builder
    public SubProductCatalogResponse(Long id, Long productId, int discount, String image, int quantity, String name,
                                     BigDecimal price) {
        this.subProductId = id;
        this.productId = productId;
        this.discount = discount;
        this.image = image;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

}
