package com.github.konradb8.collectionbox.service;

import com.github.konradb8.collectionbox.model.box.CollectionBox;
import com.github.konradb8.collectionbox.model.box.CollectionBoxRequest;
import com.github.konradb8.collectionbox.repository.CollectionBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CollectionBoxService {

    private final CollectionBoxRepository collectionBoxRepository;
    private final FundraisingEventService fundraisingEventService;

    public CollectionBox registerBox(CollectionBoxRequest request) {
        if(collectionBoxRepository.existsById(request.getUid())){
            throw new RuntimeException("Collection box with this UID already exists");
        }

        CollectionBox collectionBox = new CollectionBox();
        collectionBox.setUid(request.getUid());
        collectionBox.setEvent(null);
        collectionBox.setMoneyEntryList(new ArrayList<>());


        return collectionBoxRepository.save(collectionBox);
    }










}


