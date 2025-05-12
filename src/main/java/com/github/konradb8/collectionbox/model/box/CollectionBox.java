package com.github.konradb8.collectionbox.model.box;

import com.github.konradb8.collectionbox.model.event.FundraisingEvent;
import com.github.konradb8.collectionbox.model.MoneyEntry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionBox {

    @Id
    @Column(name = "UID",unique = true, nullable = false)
    private String uid;

    @ManyToOne
    private FundraisingEvent event;

    @OneToMany(mappedBy = "box",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoneyEntry> moneyEntryList = new ArrayList<>();


}
