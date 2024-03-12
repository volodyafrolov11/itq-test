package com.itqtest.order.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.entity.OrderEntity;
import com.itqtest.order.exception.IncorrectTotalAmountException;
import com.itqtest.order.exception.NonexistentOrderException;
import com.itqtest.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class OrderService {
    @Autowired
    private HttpService httpService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderRepository orderRepository;

    // Метод для создания заказа
    public OrderEntity createOrder(OrderEntity order, List<OrderDetailsEntity> orderDetailsList) throws IncorrectTotalAmountException, ParseException {
        if(order.getTotalAmount() <= 0) {
            throw new IncorrectTotalAmountException("Сумма заказа не может быть менше или равной нулю!");
        }

        // Добавляем заказ в базу данных
        JsonNode jsonNode = httpService.sendGetRequest("http://localhost:5001/number-generator/generate");
        Long orderNumber = jsonNode.get("orderNumber").asLong();
        String dateTime = jsonNode.get("dateTime").asText();

        Date orderDate = parseDate(dateTime);

        order.setId(orderNumber);
        order.setDate(orderDate);

        OrderEntity resultOrder = orderRepository.save(order);

        // Добавляем детали заказа к заказу
        for (int i = 0; i < orderDetailsList.size(); i++) {
            OrderDetailsEntity orderDetail = orderDetailsList.get(i);
            orderDetail.setOrder(resultOrder);
            orderDetail.setDetailId(i + 1);
            orderDetailsService.createOrderDetail(orderDetail);
        }

        return resultOrder;
    }

    // Метод для преобразования строки в дату
    private Date parseDate(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.parse(dateString);
    }

    // Метод для полученя заказа с максимальной суммой на определенную дату
    public OrderEntity getMaxOrderInDate(Date date) throws NonexistentOrderException {
        OrderEntity order = orderRepository.findMaxOrderInDate(date);

        if(order == null) {
            throw new NonexistentOrderException("На выбранную дату заказов не существует!");
        }

        return order;
    }

    // Метод для получения списка id заказов без выбранного продукта в оопределенный период времени
    public List<Long> getOrderIdsWithoutItemInPeriod(Date startPeriod, Date endPeriod, String productName) {
        return orderRepository.findOrderIdsWithoutProductInPeriod(productName, startPeriod, endPeriod);
    }
}
