package peaksoft.house.gadgetariumb9.dto.request.subProduct;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubProductCatalogRequest {

  private String gadgetType;

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

  private List<Integer> sim;

  private List<String> processors;

  private List<String> screenResolution;

  private List<String> purposes;

  private List<Integer> videoMemory;

  private List<Double> screenSize;

  private List<String> screenDiagonal;

  private List<String> batteryCapacity;

  private List<String> housingMaterials;

  private List<String> materialBracelets;

  private List<String> genders;

  private List<String> interfaces;

  private List<String> hullShapes;

}
