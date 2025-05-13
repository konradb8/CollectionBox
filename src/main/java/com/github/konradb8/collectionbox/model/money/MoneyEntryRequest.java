package com.github.konradb8.collectionbox.model.money;

import com.github.konradb8.collectionbox.model.currency.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MoneyEntryRequest {
    private Currency currency;
    private BigDecimal amount;
    private String boxUid;
}




