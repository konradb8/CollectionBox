package com.github.konradb8.collectionbox.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundraisingEventRequest {
    private String name;
    private String currency;
}
