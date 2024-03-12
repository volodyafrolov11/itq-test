package com.itqtest.order.dto;

import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.entity.OrderEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CreateOrderDto {
    private OrderEntity order;
    private List<OrderDetailsEntity> orderDetailsList;

    public CreateOrderDto(OrderEntity order, List<OrderDetailsEntity> orderDetailsList) {
        this.order = order;
        this.orderDetailsList = orderDetailsList;
    }

    public CreateOrderDto() {}

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public List<OrderDetailsEntity> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetailsEntity> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}
