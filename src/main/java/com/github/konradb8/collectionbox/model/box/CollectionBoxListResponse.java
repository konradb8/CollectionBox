package com.github.konradb8.collectionbox.model.box;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionBoxListResponse {
    private String uid;
    private Boolean isAssigned;
    private Boolean isEmpty;
}
