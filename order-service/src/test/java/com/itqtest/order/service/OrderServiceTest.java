package com.itqtest.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.entity.OrderEntity;
import com.itqtest.order.exception.IncorrectTotalAmountException;
import com.itqtest.order.exception.NonexistentOrderException;
import com.itqtest.order.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private HttpService httpService;

    @Mock
    private OrderDetailsService orderDetailsService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder() throws JsonProcessingException, IncorrectTotalAmountException, ParseException {
        OrderEntity order = new OrderEntity();
        order.setTotalAmount(400);

        List<OrderDetailsEntity> orderDetailsList = new ArrayList<>();

        OrderDetailsEntity orderDetail = new OrderDetailsEntity();
        orderDetail.setProductName("Product 1");
        orderDetail.setProductQuantity(2);

        orderDetailsList.add(orderDetail);

        String jsonResponse = "{\"orderNumber\": 240312806962855, \"dateTime\": \"2024-03-12 11:20:13\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        when(httpService.sendGetRequest(anyString())).thenReturn(objectMapper.readValue(jsonResponse, JsonNode.class));

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        doNothing().when(orderDetailsService).createOrderDetail(any(OrderDetailsEntity.class));

        OrderEntity orderEntityResult = orderService.createOrder(order, orderDetailsList);

        assertNotNull(orderEntityResult);
        verify(httpService, times(1)).sendGetRequest(anyString());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderDetailsService, times(orderDetailsList.size())).createOrderDetail(any(OrderDetailsEntity.class));
    }

    @Test(expected = IncorrectTotalAmountException.class)
    public void testCreateOrderNegativeTotalAmount() throws IncorrectTotalAmountException, ParseException {
        OrderEntity order = new OrderEntity();
        order.setTotalAmount(-200);
        List<OrderDetailsEntity> orderDetailsList = new ArrayList<>();

        orderService.createOrder(order, orderDetailsList);
    }

    @Test
    public void testGetMaxOrderInDate() throws NonexistentOrderException {
        Date date = new Date();

        OrderEntity expectedOrder = new OrderEntity();
        expectedOrder.setId(1L);
        expectedOrder.setTotalAmount(500);
        expectedOrder.setDate(date);

        when(orderRepository.findMaxOrderInDate(any(Date.class))).thenReturn(expectedOrder);

        OrderEntity orderEntityResult = orderService.getMaxOrderInDate(date);

        assertNotNull(orderEntityResult);
        assertEquals(expectedOrder, orderEntityResult);
        verify(orderRepository, times(1)).findMaxOrderInDate(any(Date.class));
    }

    @Test(expected = NonexistentOrderException.class)
    public void testGetMaxOrderInDateNoOrders() throws NonexistentOrderException {
        Date date = new Date();

        when(orderRepository.findMaxOrderInDate(any(Date.class))).thenReturn(null);

        orderService.getMaxOrderInDate(date);
    }

    @Test
    public void testGetOrderIdsWithoutItemInPeriod() {
        Date startPeriod = new Date();
        Date endPeriod = new Date();
        String productName = "PRODUCT";

        List<Long> expectedOrderIds = new ArrayList<>();
        expectedOrderIds.add(1L);
        expectedOrderIds.add(3L);

        when(orderRepository.findOrderIdsWithoutProductInPeriod(productName, startPeriod, endPeriod))
                .thenReturn(expectedOrderIds);

        List<Long> result = orderService.getOrderIdsWithoutItemInPeriod(startPeriod, endPeriod, productName);

        assertNotNull(result);
        assertEquals(expectedOrderIds, result);
        verify(orderRepository, times(1))
                .findOrderIdsWithoutProductInPeriod(productName, startPeriod, endPeriod);
    }
}
