package com.itqtest.order.entity;

import java.io.Serializable;

public class OrderDetailsPrimaryKey implements Serializable {
    private Long orderId;
    private Long detailId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }
}
