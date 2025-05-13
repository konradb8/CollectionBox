package com.github.konradb8.collectionbox.model.event;

import com.github.konradb8.collectionbox.model.currency.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundraisingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal amount = BigDecimal.ZERO;

    public boolean isEmpty(){
        return name == null | name.isEmpty();
    }
}
