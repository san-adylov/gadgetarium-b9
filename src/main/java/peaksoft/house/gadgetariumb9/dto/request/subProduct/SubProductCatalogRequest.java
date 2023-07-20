package peaksoft.house.gadgetariumb9.dto.request.subProduct;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.house.gadgetariumb9.enums.Gender;
import peaksoft.house.gadgetariumb9.enums.HousingMaterial;
import peaksoft.house.gadgetariumb9.enums.HullShape;
import peaksoft.house.gadgetariumb9.enums.Interface;
import peaksoft.house.gadgetariumb9.enums.MaterialBracelet;
import peaksoft.house.gadgetariumb9.enums.Processor;
import peaksoft.house.gadgetariumb9.enums.Purpose;

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

  private int sim;

  private List<Processor> processors;

  private List<String> screenResolution;

  private List<Purpose> purposes;

  private List<Integer> videoMemory;

  private List<Double> screenSize;

  private List<String> screenDiagonal;

  private List<String> batteryCapacity;

  private List<HousingMaterial> housingMaterials;

  private List<MaterialBracelet> materialBracelets;

  private List<Gender> genders;

  private boolean isWaterproof;

  private List<Interface> interfaces;

  private List<HullShape> hullShapes;

  @Builder
  public SubProductCatalogRequest(List<Long> brandIds, BigDecimal priceStart, BigDecimal priceEnd,
      List<String> codeColor, List<Integer> rom, List<Integer> ram, String novelties,
      String byShare,
      String recommended, String byPriceIncrease, String byDecreasingPrice, int sim,
      List<Processor> processors, List<String> screenResolution, List<Purpose> purposes,
      List<Integer> videoMemory, List<Double> screenSize, List<String> screenDiagonal,
      List<String> batteryCapacity, List<HousingMaterial> housingMaterials,
      List<MaterialBracelet> materialBracelets, List<Gender> genders, boolean isWaterproof,
      List<Interface> interfaces, List<HullShape> hullShapes) {
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
    this.sim = sim;
    this.processors = processors;
    this.screenResolution = screenResolution;
    this.purposes = purposes;
    this.videoMemory = videoMemory;
    this.screenSize = screenSize;
    this.screenDiagonal = screenDiagonal;
    this.batteryCapacity = batteryCapacity;
    this.housingMaterials = housingMaterials;
    this.materialBracelets = materialBracelets;
    this.genders = genders;
    this.isWaterproof = isWaterproof;
    this.interfaces = interfaces;
    this.hullShapes = hullShapes;
  }
}
