package com.itqtest.order.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    @Hidden
    private Long id;

    @Column(name = "order_total_amount")
    private Integer totalAmount;

    @Column(name = "order_date")
    @Hidden
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetailsEntity> orderDetails;

    public OrderEntity(Long id, Integer totalAmount, Date date) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    public OrderEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
