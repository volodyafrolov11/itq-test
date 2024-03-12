package com.itqtest.order.service;

import static org.mockito.Mockito.*;

import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.repository.OrderDetailsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailsServiceTest {

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderDetailsService orderDetailsService;

    @Test
    public void testCreateOrderDetail() {
        OrderDetailsEntity orderDetail = new OrderDetailsEntity();

        orderDetailsService.createOrderDetail(orderDetail);

        verify(orderDetailsRepository, times(1)).save(orderDetail);
    }

}
