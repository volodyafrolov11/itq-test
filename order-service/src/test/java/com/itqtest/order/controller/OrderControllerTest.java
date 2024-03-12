package com.itqtest.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itqtest.order.dto.CreateOrderDto;
import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.entity.OrderEntity;
import com.itqtest.order.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testCreateSuccess() throws Exception {
        String requestUrl = "/orders";

        OrderEntity order = new OrderEntity();
        List<OrderDetailsEntity> orderDetailsList = Collections.emptyList();

        when(orderService.createOrder(any(OrderEntity.class), anyList())).thenReturn(order);

        CreateOrderDto orderDto = new CreateOrderDto();
        orderDto.setOrder(order);
        orderDto.setOrderDetailsList(orderDetailsList);

        mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getMaxOrderInDateSuccess() throws Exception {
        String requestUrl = "/orders/max-order-in-date?date=2024-03-11";

        when(orderService.getMaxOrderInDate(new Date())).thenReturn(new OrderEntity());

        mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getOrderIdsWithoutItemInPeriodSuccess() throws Exception {
        String requestUrl = "/orders/without-item-in-period?startPeriod=2024-03-08&endPeriod=2024-03-11&productName=PRODUCT";

        List<Long> expectedIds = new ArrayList<>();

        when(orderService.getOrderIdsWithoutItemInPeriod(new Date(), new Date(), "PRODUCT"))
                .thenReturn(expectedIds);

        mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

