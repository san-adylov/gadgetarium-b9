package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class OrderHistoryResponse {

    private Long orderId;

    private String date;

    private int orderNumber;

    private String status;

    private BigDecimal totalPrice;

    public OrderHistoryResponse(Long orderId, Date date, int orderNumber, String status, BigDecimal totalPrice) {
        this.orderId = orderId;
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = dateTimeFormatter.format(zonedDateTime);
        this.orderNumber = orderNumber;
        this.status = status;
        this.totalPrice = totalPrice;
    }


}
