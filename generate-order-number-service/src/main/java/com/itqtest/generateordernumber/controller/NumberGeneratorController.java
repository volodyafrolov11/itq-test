package com.itqtest.generateordernumber.controller;

import com.itqtest.generateordernumber.service.NumberGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Методы генерации номера заказа")
@RestController
@RequestMapping("/number-generator")
public class NumberGeneratorController {

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    @Operation(
        summary = "Возвращает уникальный номер заказа (формат: YYMMDD + 9 случайных чисел) и текущую дату и время"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\"orderNumber\": 20240311748593857, \"dateTime\": \"2024-03-11 15:32:15\"}"
                )
            )
        )
    })
    @GetMapping("/generate")
    public ResponseEntity getOrderNumber() {
        try {
            return ResponseEntity.ok(numberGeneratorService.generateNumber());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла непредвиденная ошибка!");
        }
    }

}
