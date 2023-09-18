package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.BadCredentialException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.Discount;
import peaksoft.house.gadgetariumb9.repositories.DiscountRepository;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.services.DiscountService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    private final SubProductRepository subProductRepository;

    @Override
    public SimpleResponse saveDiscount(DiscountRequest discountRequest) {
        if (discountRequest.getDiscountStartDate().isAfter(discountRequest.getDiscountEndDate()) ||
            discountRequest.getDiscountStartDate().isEqual(discountRequest.getDiscountEndDate()) ||
            discountRequest.getDiscountStartDate().isBefore(LocalDate.now())) {
            throw new BadCredentialException("Invalid date range. Start date must be in the present or future, and end date must be after start date.");
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
