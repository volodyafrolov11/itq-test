package com.itqtest.order.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.stereotype.Service;

@Service
public class HttpService {
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Метод для отправки HTTP GET запроса
    public JsonNode sendGetRequest(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return objectMapper.readTree(response.getEntity().getContent());
        } catch (Exception e) {
            System.err.println("Произошла ошибка при отправке GET-запроса: " + e.getMessage());
            return null;
        }
    }

}
