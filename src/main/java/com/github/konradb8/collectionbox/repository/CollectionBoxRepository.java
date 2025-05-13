package com.github.konradb8.collectionbox.repository;

import com.github.konradb8.collectionbox.model.box.CollectionBox;
import com.github.konradb8.collectionbox.model.event.FundraisingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionBoxRepository extends JpaRepository<CollectionBox, String> {
    CollectionBox findByUid(String uid);
    List<CollectionBox> findByEvent(FundraisingEvent event);
}
