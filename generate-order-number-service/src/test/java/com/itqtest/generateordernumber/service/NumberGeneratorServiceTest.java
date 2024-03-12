package com.itqtest.generateordernumber.service;

import com.itqtest.generateordernumber.types.OrderResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class NumberGeneratorServiceTest {

    @InjectMocks
    private NumberGeneratorService numberGeneratorService;

    @Test
    public void testGenerateNumber() {
        OrderResponse orderNumberResult = numberGeneratorService.generateNumber();
        Assertions.assertNotNull(orderNumberResult);
        // Проверяем формат номера
        Assertions.assertTrue(orderNumberResult.getOrderNumber().toString().matches("\\d{15}"));
        // Проверяем формат даты
        Assertions.assertTrue(orderNumberResult.getDateTime().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    public void testUniqueOrderNumbers() {
        Set<String> orderNumbers = new HashSet<>();
        int numberOfIterations = 100;

        for (int i = 0; i < numberOfIterations; i++) {
            String orderNumber = numberGeneratorService.generateNumber().getOrderNumber().toString();
            Assertions.assertFalse(orderNumbers.contains(orderNumber));
            orderNumbers.add(orderNumber);
        }
    }

}
