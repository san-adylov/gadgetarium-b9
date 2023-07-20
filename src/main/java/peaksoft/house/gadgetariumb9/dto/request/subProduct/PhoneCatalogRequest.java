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
public class PhoneCatalogRequest {

  private List<Long> brandIs;

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

  private int sim;

  private List<String> batteryCapacity;

  private List<Double> screenSize;

  private List<String> screenDiagonal;

  private List<String> screenResolution;

  @Builder
  public PhoneCatalogRequest(List<Long> brandIs, BigDecimal priceStart, BigDecimal priceEnd,
      List<String> codeColor, List<Integer> rom, List<Integer> ram, String novelties,
      String byShare,
      String recommended, String byPriceIncrease, String byDecreasingPrice, int sim,
      List<String> batteryCapacity, List<Double> screenSize, List<String> screenDiagonal,
      List<String> screenResolution) {
    this.brandIs = brandIs;
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
    this.sim = sim;
    this.batteryCapacity = batteryCapacity;
    this.screenSize = screenSize;
    this.screenDiagonal = screenDiagonal;
    this.screenResolution = screenResolution;
  }
}
