package com.itqtest.generateordernumber.types;

public class OrderResponse {
    private Long orderNumber;
    private String dateTime;

    public OrderResponse(Long orderNumber, String dateTime) {
        this.orderNumber = orderNumber;
        this.dateTime = dateTime;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
