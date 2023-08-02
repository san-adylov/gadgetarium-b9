package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderInfoResponse;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderPaginationAdmin;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderProductResponse;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderResponseAdmin;
import peaksoft.house.gadgetariumb9.exceptions.BadRequestException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.template.OrderTemplate;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderTemplateImpl implements OrderTemplate {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public OrderPaginationAdmin getAllOrderAdmin(String status, LocalDate startDate, LocalDate endDate, int pageSize, int pageNumber) {
        String query = "SELECT sum(s.quantity) from sub_products s";
        String query2 = "SELECT sum(o.quantity) from orders o";

        Integer subProductQuantityCount = jdbcTemplate.queryForObject(query, Integer.class);
        Integer orderQuantityCount = jdbcTemplate.queryForObject(query2, Integer.class);

        int difference = (subProductQuantityCount != null ? subProductQuantityCount : 0) - (orderQuantityCount != null ? orderQuantityCount : 0);

        String numberIN_PROCESSING = "SELECT SUM(CASE WHEN o.status = 'IN_PROCESSING' THEN 1 ELSE 0 END) AS sum_in_processing\n" +
                "FROM orders o;";
        Integer number1 = jdbcTemplate.queryForObject(numberIN_PROCESSING, Integer.class);

        String numberREADY_FOR_DELIVERY = "SELECT SUM(CASE WHEN o.status = 'READY_FOR_DELIVERY' THEN 1 ELSE 0 END) AS sum_in_processing\n" +
                "FROM orders o";
        Integer number2 = jdbcTemplate.queryForObject(numberREADY_FOR_DELIVERY, Integer.class);

        String numberDELIVERED = "SELECT SUM(CASE WHEN o.status = 'DELIVERED' THEN 1 ELSE 0 END) AS sum_in_processing\n" +
                "FROM orders o";
        Integer number3 = jdbcTemplate.queryForObject(numberDELIVERED, Integer.class);
        String sql = "";

        if (status != null) {
            if (status.equalsIgnoreCase("В ожидании")) {
                sql += """
                        SELECT o.id, concat(u.first_name,' ',u.last_name) as full_name, o.order_number,o.quantity, o.total_price,type_delivery,status
                               FROM orders o join public.users u on u.id = o.user_id where o.status= 'PENDING'
                        """;
            } else if (status.equalsIgnoreCase("В обработке")) {
                sql += """
                        SELECT o.id, concat(u.first_name,' ',u.last_name) as full_name, o.order_number,o.quantity, o.total_price,type_delivery,status
                               FROM orders o join public.users u on u.id = o.user_id where o.status= 'IN_PROCESSING'
                        """;
            } else if (status.equalsIgnoreCase("Курьер в пути")) {
                sql += """
                        SELECT o.id, concat(u.first_name,' ',u.last_name) as full_name, o.order_number,o.quantity, o.total_price,type_delivery,status
                               FROM orders o join public.users u on u.id = o.user_id where o.status= 'COURIER_ON_THE_WAY'
                        """;
            } else if (status.equalsIgnoreCase("Доставлен")) {
                sql += """
                        SELECT o.id, concat(u.first_name,' ',u.last_name) as full_name, o.order_number,o.quantity, o.total_price,type_delivery,status
                               FROM orders o join public.users u on u.id = o.user_id where o.status= 'DELIVERED'
                        """;
            } else if (status.equalsIgnoreCase("Отменить")) {
                sql += """
                        SELECT o.id, concat(u.first_name,' ',u.last_name) as full_name, o.order_number,o.quantity, o.total_price,type_delivery,status
                               FROM orders o join public.users u on u.id = o.user_id where o.status= 'CANCEL'
                        """;
            }
        } else {
            log.error("Order status is not correct");
            throw new BadRequestException("Order status is not correct");
        }

        int offset = (pageNumber - 1) * pageSize;
        sql += "AND o.date_of_order >= ? AND o.date_of_order <= ?";
        sql += " LIMIT ? OFFSET ?";

        List<OrderResponseAdmin> orderResponseAdmins = jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        OrderResponseAdmin.builder()
                                .id(rs.getLong("id"))
                                .fullName(rs.getString("full_name"))
                                .orderNumber(rs.getInt("order_number"))
                                .quantity(rs.getInt("quantity"))
                                .totalPrice(rs.getBigDecimal("total_price"))
                                .typeDelivery(rs.getString("type_delivery"))
                                .status(rs.getString("status"))
                                .build(),
                startDate, endDate,
                pageSize, offset
        );

        log.info("Successfully");
        return new OrderPaginationAdmin(pageSize, pageNumber, difference, number1, number2, number3, orderResponseAdmins);
    }

    @Override
    public OrderInfoResponse getOrderInfo(Long orderId) {
        String sql = """
                SELECT    o.order_number,
                o.id,
                          o.status,
                          u.phone_number,
                          u.address  from orders o join user_favorite uf on o.user_id = uf.user_id join users u on u.id = o.user_id where o.id =? and o.status = 'DELIVERED'
                """;
        OrderInfoResponse orderInfoResponse = jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        OrderInfoResponse
                                .builder()
                                .orderId(rs.getLong("id"))
                                .orderNumber(rs.getInt("order_number"))
                                .status(rs.getString("status"))
                                .phoneNumber(rs.getString("phone_number"))
                                .address(rs.getString("address")), orderId).stream().findFirst().orElseThrow(() -> new NotFoundException("Order by id %s is not found.".formatted(orderId))).build();
        String sql2 = """
                    SELECT
                        o.order_number,
                        concat(p.name, ' ', b.name, ' ', sp.rom, ' ', sp.code_color) AS name,
                        sp.quantity,
                        o.total_price,
                        o.total_discount,
                        (o.total_discount * sp.price / 100) AS sumOfDiscount,
                        COALESCE(SUM(sp.price * (1 - d.sale / 100.0)), 0) AS total
                    FROM orders o
                             JOIN orders_sub_products osp ON o.id = osp.orders_id
                             JOIN sub_products sp ON sp.id = osp.sub_products_id
                             JOIN products p ON p.id = sp.product_id
                             JOIN brands b ON b.id = p.brand_id
                             LEFT JOIN public.discounts d ON sp.id = d.sub_product_id where o.id = ?
                    GROUP BY o.id, p.name, b.name, sp.rom, sp.code_color, sp.quantity, o.total_price, o.total_discount, sp.price
                """;
        List<OrderProductResponse> orderProductResponse = jdbcTemplate.query(sql2, (rs, i) ->
                OrderProductResponse
                        .builder()
                        .orderNumber(rs.getInt("order_number"))
                        .name(rs.getString("name"))
                        .quantity(rs.getInt("quantity"))
                        .totalAmountOfOrder(rs.getBigDecimal("total_price"))
                        .sale(rs.getInt("total_discount"))
                        .sumOfDiscount(rs.getBigDecimal("sumOfDiscount"))
                        .total(rs.getBigDecimal("total")).build(), orderId
        );
        orderInfoResponse.setProductResponseList(orderProductResponse);
        return orderInfoResponse;
    }
}
