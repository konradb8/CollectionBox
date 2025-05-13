package com.github.konradb8.collectionbox.model.money;

import com.github.konradb8.collectionbox.model.currency.Currency;
import com.github.konradb8.collectionbox.model.box.CollectionBox;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoneyEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal amount;

    @ManyToOne
    private CollectionBox box;

}
