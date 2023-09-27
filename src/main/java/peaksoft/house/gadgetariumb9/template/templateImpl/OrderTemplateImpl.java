package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.order.*;
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

        String numberIN_PROCESSING = """
                SELECT SUM(CASE WHEN o.status = 'IN_PROCESSING' THEN 1 ELSE 0 END) AS sum_in_processing
                FROM orders o;
                """;
        Integer number1 = jdbcTemplate.queryForObject(numberIN_PROCESSING, Integer.class);

        String numberREADY_FOR_DELIVERY = """
                SELECT SUM(CASE WHEN o.status = 'READY_FOR_DELIVERY' THEN 1 ELSE 0 END) AS sum_in_processing
                FROM orders o
                """;
        Integer number2 = jdbcTemplate.queryForObject(numberREADY_FOR_DELIVERY, Integer.class);

        String numberDELIVERED = """
                SELECT SUM(CASE WHEN o.status = 'DELIVERED' THEN 1 ELSE 0 END) AS sum_in_processing
                FROM orders o
                               """;
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
            } else if (status.equalsIgnoreCase("Получен")) {
                sql += """
                        SELECT o.id, concat(u.first_name,' ',u.last_name) as full_name, o.order_number,o.quantity, o.total_price,type_delivery,status
                                                       FROM orders o join public.users u on u.id = o.user_id where o.status= 'RECEIVED'
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
                                .orderId(rs.getLong("id"))
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

        if (number1 == null || number2 == null || number3 == null) {
            throw new NullPointerException("Number is null");
        }
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
                          u.address  from orders o left join users u on o.user_id = u.id where o.id = ?
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
                           (SELECT CONCAT(p.name, ' ', b.name, ' ', sp.rom, ' ', sp.code_color)
                            FROM orders_sub_products osp
                            JOIN sub_products sp ON sp.id = osp.sub_products_id
                            JOIN products p ON p.id = sp.product_id
                            WHERE osp.orders_id = o.id LIMIT 1) AS names,
                           o.quantity,
                           o.total_price,
                           o.total_discount,
                           (o.total_price - SUM(CAST(COALESCE(sp.price * (1 - CAST(d.sale AS DECIMAL) / 100), 0) AS DECIMAL))) AS sum_of_discount,
                           SUM(CAST(COALESCE(sp.price * (1 - CAST(d.sale AS DECIMAL) / 100), 0) AS DECIMAL)) AS total
                           FROM orders o
                                JOIN orders_sub_products osp ON o.id = osp.orders_id
                                JOIN sub_products sp ON sp.id = osp.sub_products_id
                                JOIN products p ON p.id = sp.product_id
                                JOIN brands b ON b.id = p.brand_id
                                JOIN discounts d ON sp.id = d.sub_product_id
                           WHERE o.id = ?
                           GROUP BY o.id, o.order_number, o.quantity, o.total_price, o.total_discount, b.name
                    """;
        OrderProductResponse response = jdbcTemplate.queryForObject(sql2, (rs, rowNum) -> OrderProductResponse.builder()
            .orderNumber(rs.getInt("order_number"))
            .names(rs.getString("names"))
            .quantity(rs.getInt("quantity"))
            .allPrice(rs.getBigDecimal("total_price"))
            .sale(rs.getInt("total_discount"))
            .sumOfDiscount(rs.getBigDecimal("sum_of_discount"))
            .totalPrice(rs.getBigDecimal("total"))
            .build(), orderId);

        orderInfoResponse.setProductResponseList(response);
        return orderInfoResponse;
    }

    @Override
    public List<OrderHistoryResponse> getOrdersByUserId(Long userId) {
        String sql = """
                SELECT o.id,
                       o.date_of_order,
                       o.order_number,
                       o.status,
                       o.total_price
                FROM orders o
                         JOIN users u ON o.user_id = u.id
                WHERE u.id = ?
                """;
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new OrderHistoryResponse(
                        rs.getLong("id"),
                        rs.getDate("date_of_order"),
                        rs.getInt("order_number"),
                        rs.getString("status"),
                        rs.getBigDecimal("total_price"))
                , userId

        );
    }

    @Override
    public OrderInfoByUserResponse getOrderByUser(Long orderId, Long userId) {

        String sql = """
                SELECT
                    o.id                                    as order_id,
                    o.order_number                          as order_number,
                    o.status                                as status,
                    concat(u.first_name,' ',u.last_name)    as client,
                    u.first_name                            as first_name,
                    u.last_name                             as last_name,
                    u.address                               as address,
                    u.phone_number                          as phone_number,
                    u.email                                 as email,
                    o.date_of_order                         as date,
                    o.type_payment                          as type_payment,
                    o.total_discount                        as total_discount,
                    o.total_price                           as total_price
                FROM orders o
                JOIN users u on o.user_id = u.id
                JOIN orders_sub_products osp on o.id = osp.orders_id
                JOIN sub_products sp on osp.sub_products_id = sp.id
                JOIN products p on sp.product_id = p.id
                JOIN brands b on p.brand_id = b.id
                LEFT JOIN discounts d on sp.id = d.sub_product_id
                WHERE o.id = ? AND u.id = ?
                """;

        OrderInfoByUserResponse info = new OrderInfoByUserResponse();
        jdbcTemplate.query(sql, (rs, rowNum) -> {
                    info.setOrderId(rs.getLong("order_id"));
                    info.setOrderNumber(rs.getInt("order_number"));
                    info.setStatus(rs.getString("status"));
                    info.setClient(rs.getString("client"));
                    info.setFirstName(rs.getString("first_name"));
                    info.setLastName(rs.getString("last_name"));
                    info.setAddress(rs.getString("address"));
                    info.setPhoneNumber(rs.getString("phone_number"));
                    info.setEmail(rs.getString("email"));
                    info.setDate(rs.getDate("date").toLocalDate());
                    info.setTypePayment(rs.getString("type_payment"));
                    info.setTotalDiscount(rs.getInt("total_discount"));
                    info.setTotalPrice(rs.getInt("total_price"));
                    return info;
                },
                orderId,
                userId
        );

        String sql2 = """
                SELECT
                    sp.id,
                    concat(p.name, ' ', b.name, ' ', sp.rom, ' ', sp.code_color)    as product_name,
                    sp.rating                                                       as rating,
                    (SELECT COUNT(r) FROM sub_products sp
                    JOIN reviews r ON sp.id = r.sub_product_id
                    JOIN orders_sub_products osp on sp.id = osp.sub_products_id)    as count_of_reviews,
                    sp.price                                                        as price,
                    (SELECT spi.images
                        FROM sub_product_images spi
                        WHERE spi.sub_product_id = sp.id LIMIT 1)                   as image
                FROM sub_products sp
                JOIN products p on sp.product_id = p.id
                JOIN reviews r on sp.id = r.sub_product_id
                JOIN brands b on p.brand_id = b.id
                JOIN orders_sub_products osp on sp.id = osp.sub_products_id
                JOIN orders o on osp.orders_id = o.id
                JOIN users u on o.user_id = u.id
                WHERE o.id = ? AND u.id = ?
                """;

        List<OrderProductsInfoResponse> products = jdbcTemplate.query(
                sql2, (rs, i) -> new OrderProductsInfoResponse(
                        rs.getLong("id"),
                        rs.getString("product_name"),
                        rs.getDouble("rating"),
                        rs.getInt("count_of_reviews"),
                        rs.getBigDecimal("price"),
                        rs.getString("image")
                ),
                orderId,
                userId
        );

        info.setProductsInfoResponses(products);
        return info;
    }
}
