package com.itqtest.order.repository;

public class OrderQuery {
    public static final String FIND_MAX_ORDER_IN_DATE =
            "select o from OrderEntity o where cast(o.date as date) = :date order by o.totalAmount desc limit 1";
    public static final String FIND_ORDER_IDS_WITHOUT_PRODUCT_IN_PERIOD =
            "select o.id from OrderEntity o where o.id not in " +
            "(select od.order.id from OrderDetailsEntity od where od.productName = :productName) " +
            "and o.date between :startDate and :endDate";
}
