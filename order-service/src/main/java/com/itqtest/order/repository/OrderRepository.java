package com.itqtest.order.repository;

import com.itqtest.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(OrderQuery.FIND_MAX_ORDER_IN_DATE)
    OrderEntity findMaxOrderInDate(Date date);

    @Query(OrderQuery.FIND_ORDER_IDS_WITHOUT_PRODUCT_IN_PERIOD)
    List<Long> findOrderIdsWithoutProductInPeriod(String productName, Date startDate, Date endDate);
}
