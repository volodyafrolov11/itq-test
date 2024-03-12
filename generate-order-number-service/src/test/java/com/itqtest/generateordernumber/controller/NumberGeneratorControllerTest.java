package com.itqtest.generateordernumber.controller;

import com.itqtest.generateordernumber.service.NumberGeneratorService;
import com.itqtest.generateordernumber.types.OrderResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NumberGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private NumberGeneratorService numberGeneratorService;

    @InjectMocks
    private NumberGeneratorController numberGeneratorController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(numberGeneratorController).build();
    }

    @Test
    public void testGenerateNumberSuccess() throws Exception {
        // Настроим мок сервиса
        Long orderNumber = 240312975037586L;
        String dateTime = "2024-03-12 01:41:14";
        Mockito.when(numberGeneratorService.generateNumber()).thenReturn(new OrderResponse(orderNumber, dateTime));

        mockMvc.perform(MockMvcRequestBuilders.get("/number-generator/generate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderNumber").value(orderNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value(dateTime));
    }

    @Test
    public void testGenerateNumberExceptionHandling() throws Exception {
        Mockito.when(numberGeneratorService.generateNumber()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/number-generator/generate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}
