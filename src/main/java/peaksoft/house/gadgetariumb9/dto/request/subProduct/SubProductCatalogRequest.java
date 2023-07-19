package peaksoft.house.gadgetariumb9.dto.request.subProduct;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubProductCatalogRequest {

  private List<Long> brandIds;

  private BigDecimal priceStart;

  private BigDecimal priceEnd;

  private List<String> codeColor;

  private List<Integer> rom;

  private List<Integer> ram;

  private String novelties;

  private String byShare;

  private String recommended;

  private String byPriceIncrease;

  private String byDecreasingPrice;

  private int

  @Builder
  public SubProductCatalogRequest(List<Long> brandIds, BigDecimal priceStart, BigDecimal priceEnd,
      List<String> codeColor, List<Integer> rom, List<Integer> ram, String novelties,
      String byShare,
      String recommended, String byPriceIncrease, String byDecreasingPrice) {
    this.brandIds = brandIds;
    this.priceStart = priceStart;
    this.priceEnd = priceEnd;
    this.codeColor = codeColor;
    this.rom = rom;
    this.ram = ram;
    this.novelties = novelties;
    this.byShare = byShare;
    this.recommended = recommended;
    this.byPriceIncrease = byPriceIncrease;
    this.byDecreasingPrice = byDecreasingPrice;
  }
}
