package com.github.konradb8.collectionbox.model.currency;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ExchangeRateResponse {
    private Map<String, BigDecimal> rates;
}
