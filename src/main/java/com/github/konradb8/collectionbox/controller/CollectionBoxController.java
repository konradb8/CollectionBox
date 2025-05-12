package com.github.konradb8.collectionbox.controller;


import com.github.konradb8.collectionbox.model.box.CollectionBox;
import com.github.konradb8.collectionbox.model.box.CollectionBoxRequest;
import com.github.konradb8.collectionbox.service.CollectionBoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/v1/collection-box")
public class CollectionBoxController {

    public final CollectionBoxService collectionBoxService;

    public CollectionBoxController(CollectionBoxService collectionBoxService) {
        this.collectionBoxService = collectionBoxService;
    }
    @PostMapping
    public ResponseEntity<?> createCollectionBox(@RequestBody CollectionBoxRequest request) {
        CollectionBox collectionBox = collectionBoxService.registerBox(request);
        return ResponseEntity.ok(Collections.singletonMap("message","Collection Box Created"));
    }
}
