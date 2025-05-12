package com.github.konradb8.collectionbox.model.event;

import com.github.konradb8.collectionbox.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public class FundraisingEventReportResponse {
    private String name;
    private BigDecimal amount;
    private Currency currency;
}
