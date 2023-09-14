package com.metamafitness.fitnessbackend.service.impl;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static com.metamafitness.fitnessbackend.common.CoreConstant.Exception.CURRENCY_CONVERSION_EXCEPTION;
import com.metamafitness.fitnessbackend.exception.CurrencyConversionException;
import com.metamafitness.fitnessbackend.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
    @Value("${rapid-api.api-endpoint}")
    private String API_ENDPOINT;
    @Value("${rapid-api.api-key}")
    private String API_KEY ;
    @Value("${rapid-api.api_host}")
    private String API_HOST;
    public BigDecimal convert(String fromCurrency, String toCurrency, BigDecimal amount) {
        String url = API_ENDPOINT + "?have=" + fromCurrency + "&want=" + toCurrency + "&amount=" + amount;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("content-type", "application/octet-stream")
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            return jsonObject.getAsJsonPrimitive("new_amount").getAsBigDecimal();

        } catch (IOException | InterruptedException e) {
            throw new CurrencyConversionException(new CurrencyConversionException(), CURRENCY_CONVERSION_EXCEPTION, null);
        }
    }
    }


