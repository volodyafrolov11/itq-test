package com.itqtest.order.controller;

import com.itqtest.order.dto.CreateOrderDto;
import com.itqtest.order.entity.OrderDetailsEntity;
import com.itqtest.order.entity.OrderEntity;
import com.itqtest.order.exception.IncorrectTotalAmountException;
import com.itqtest.order.exception.NonexistentOrderException;
import com.itqtest.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "Методы для работы с заказами")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(
        summary = "Добавляет заказ и детали заказа в БД"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\"id\": 20240311748593857, \"totalAmount\": 100, \"date\": \"2024-03-11T13:01:01.000+00:00\"}"
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity create(@RequestBody CreateOrderDto orderDto) {
        try {
            OrderEntity order = orderDto.getOrder();
            List<OrderDetailsEntity> orderDetailsList = orderDto.getOrderDetailsList();
            return ResponseEntity.ok(orderService.createOrder(order, orderDetailsList));
        } catch (IncorrectTotalAmountException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла непредвиденная ошибка!");
        }
    }

    @Operation(
        summary = "Возвращает заказ с максимальной суммой на определенную дату"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\"id\": 20240311748593857, \"totalAmount\": 100, \"date\": \"2024-03-11T13:01:01.000+00:00\"}"
                )
            )
        )
    })
    @GetMapping("/max-order-in-date")
    public ResponseEntity getMaxOrderInDate(
            @Parameter(description = "Дата", example = "2024-03-10")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam
            Date date
    ) {
        try {
            return ResponseEntity.ok(orderService.getMaxOrderInDate(date));
        } catch (NonexistentOrderException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла непредвиденная ошибка!" + e.getMessage());
        }
    }

    @Operation(
        summary = "Возвращает номера заказов без выбранного продукта в рамках определенного периода времени"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Возвращает массив номеров заказов без выбранного продукта в рамках определенного периода времени",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "[240310799393445, 240310682132599, 240310839541457]"
                )
            )
        )
    })
    @GetMapping("/without-item-in-period")
    public ResponseEntity getOrderIdsWithoutItemInPeriod(
            @Parameter(description = "дата начала перода", example = "2024-03-08")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam
            Date startPeriod,
            @Parameter(description = "дата окончания перода", example = "2024-03-11")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam
            Date endPeriod,
            @Parameter(description = "Название продукиа", example = "Product")
            @RequestParam
            String productName
    ) {
        try {
            return ResponseEntity.ok(orderService.getOrderIdsWithoutItemInPeriod(startPeriod, endPeriod, productName));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла непредвиденная ошибка!");
        }
    }
}
