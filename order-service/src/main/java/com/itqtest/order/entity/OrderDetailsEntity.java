package com.itqtest.order.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
@IdClass(OrderDetailsEntity.class)
public class OrderDetailsEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    @Hidden
    private OrderEntity order;

    @Id
    @Column(name = "detail_id")
    @Hidden
    private Integer detailId;

    @Column(name = "detail_product_name")
    private String productName;

    @Column(name = "detail_product_quantity")
    private Integer productQuantity;

    public OrderDetailsEntity(OrderEntity order, Integer detailId, String productName, Integer productQuantity) {
        this.order = order;
        this.detailId = detailId;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public OrderDetailsEntity() {}

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
