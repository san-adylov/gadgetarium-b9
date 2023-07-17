package peaksoft.house.gadgetariumb9.service.serviceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;
import peaksoft.house.gadgetariumb9.dto.response.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.entities.Discount;
import peaksoft.house.gadgetariumb9.entities.SubProduct;
import peaksoft.house.gadgetariumb9.exception.BadCredentialException;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.repository.DiscountRepository;
import peaksoft.house.gadgetariumb9.repository.SubProductRepository;
import peaksoft.house.gadgetariumb9.service.DiscountService;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

  private final DiscountRepository discountRepository;
  private final SubProductRepository subProductRepository;

  @Override
  public SimpleResponse saveDiscount(DiscountRequest discountRequest) {
    LocalDate discountStartDate = LocalDate.parse(discountRequest.discountStartDate());
    LocalDate discountEndDate = LocalDate.parse(discountRequest.discountEndDate());
    if (discountStartDate.isEqual(discountEndDate)){
      throw new BadCredentialException("The start and end of the action must not be the same");
    }
    if (discountStartDate.isAfter(LocalDate.now()) && discountEndDate.isAfter(LocalDate.now())) {
      ZoneId zoneId = ZoneId.systemDefault();
      ZonedDateTime startDate = discountStartDate.atStartOfDay(zoneId);
      ZonedDateTime endDate = discountEndDate.atStartOfDay(zoneId);
      SubProduct subProduct = subProductRepository.findById(discountRequest.subProductId())
          .orElseThrow(() -> new NotFoundException(
              "SubProduct with id: %s not found".formatted(discountRequest.subProductId())));
      Discount discount = Discount
          .builder()
          .sale(discountRequest.amountOfDiscount())
          .startDate(startDate)
          .finishDate(endDate)
          .subProduct(subProduct)
          .build();
      discountRepository.save(discount);
      return SimpleResponse
          .builder()
          .message("Discount successfully saved")
          .status(HttpStatus.OK)
          .build();
    } else {
      throw new BadCredentialException("The date must be in the future");
    }
  }
}
