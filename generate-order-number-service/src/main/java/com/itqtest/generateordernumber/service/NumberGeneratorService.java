package com.itqtest.generateordernumber.service;

import com.itqtest.generateordernumber.types.OrderResponse;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class NumberGeneratorService {
    // Метод для генерации номера заказа и текущей даты и времени
    public OrderResponse generateNumber() {
        Long uniqueNumber = generateUniqueNumber();
        String dateTime = getCurrentDateTime();

        return new OrderResponse(uniqueNumber, dateTime);
    }

    // Метод для создания номера заказа
    private Long generateUniqueNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String currentDate = dateFormat.format(new Date());

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(currentDate);
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        return Long.parseLong(stringBuilder.toString());
    }

    // Метод для получения текущей даты и времени
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat.format(new Date());
    }

}
