package peaksoft.house.gadgetariumb9.service.serviceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;
import peaksoft.house.gadgetariumb9.dto.response.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.entities.Discount;
import peaksoft.house.gadgetariumb9.exception.BadCredentialException;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.repository.DiscountRepository;
import peaksoft.house.gadgetariumb9.repository.SubProductRepository;
import peaksoft.house.gadgetariumb9.service.DiscountService;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

  private final DiscountRepository discountRepository;
  private final SubProductRepository subProductRepository;

  @Override
  public SimpleResponse saveDiscount(DiscountRequest discountRequest) {
    if (discountRequest.getDiscountStartDate().isAfter(discountRequest.getDiscountEndDate()) ||
    discountRequest.getDiscountStartDate().isEqual(discountRequest.getDiscountEndDate())) {
      throw new BadCredentialException("The start and end of the action must not be the same");
    }
    if (discountRequest.getDiscountStartDate().isBefore(LocalDate.now())
        && discountRequest.getDiscountEndDate().isBefore(LocalDate.now())) {
      throw new BadCredentialException("The date must be in the future");
    }
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime startDate = discountRequest.getDiscountStartDate().atStartOfDay(zoneId);
    ZonedDateTime endDate = discountRequest.getDiscountEndDate().atStartOfDay(zoneId);
    List<Discount> discounts = discountRequest.getSubProductIds().stream()
        .map(subProductId -> subProductRepository.findById(subProductId)
            .orElseThrow(() -> {
              log.error("SubProduct with id: %s not found".formatted(subProductId));
              return new NotFoundException(
                  "SubProduct with id: %s not found".formatted(subProductId));
            }))
        .map(subProduct -> Discount.builder()
            .sale(discountRequest.getAmountOfDiscount())
            .startDate(startDate)
            .finishDate(endDate)
            .subProduct(subProduct)
            .build())
        .collect(Collectors.toList());
    discountRepository.saveAll(discounts);
    log.info("Discount successfully saved");
    return SimpleResponse
        .builder()
        .message("Discount successfully saved")
        .status(HttpStatus.OK)
        .build();
  }
}
