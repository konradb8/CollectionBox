package com.github.konradb8.collectionbox.service;

import com.github.konradb8.collectionbox.model.currency.Currency;
import com.github.konradb8.collectionbox.model.currency.ExchangeRateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getExchangeRate(Currency from, Currency to) {
        String url = "https://api.exchangerate-api.com/v4/latest/" + from;
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        return response.getRates().get(to.toString());
    }

}