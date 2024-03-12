package com.itqtest.order.service;

import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    // Метод для создания детали заказа
    public void createOrderDetail(OrderDetailsEntity orderDetail) {
        orderDetailsRepository.save(orderDetail);
    }
}
