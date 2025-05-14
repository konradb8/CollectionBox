package com.github.konradb8.collectionbox.controller;


import com.github.konradb8.collectionbox.model.money.MoneyEntryRequest;
import com.github.konradb8.collectionbox.model.box.CollectionBoxListResponse;
import com.github.konradb8.collectionbox.model.box.CollectionBoxRequest;
import com.github.konradb8.collectionbox.service.CollectionBoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/collection-box")
public class CollectionBoxController {

    public final CollectionBoxService collectionBoxService;

    public CollectionBoxController(CollectionBoxService collectionBoxService) {
        this.collectionBoxService = collectionBoxService;
    }

    // 2
    @PostMapping
    public ResponseEntity<?> createCollectionBox(@RequestBody CollectionBoxRequest request) {
        collectionBoxService.registerBox(request);
        return ResponseEntity.ok(Collections.singletonMap("message","Collection Box Created"));
    }

    // 3
    @GetMapping("boxlist")
    public ResponseEntity<List<CollectionBoxListResponse>> getCollectionBoxList(){
        return ResponseEntity.ok(collectionBoxService.getBoxList());
    }

    // 4
    @DeleteMapping("/{box}")
    public ResponseEntity<?> deleteCollectionBox(@PathVariable String box) {
        collectionBoxService.deleteBox(box);
        return ResponseEntity.ok(Collections.singletonMap("message","Collection Box Deleted"));
    }

    // 5
    @PutMapping("/assign/{box}/{event}")
    public ResponseEntity<?> assign(@PathVariable String box, @PathVariable String event) {
        collectionBoxService.assignBox(box,event);
        return ResponseEntity.ok(Collections.singletonMap("message","Box " + box + " assigned to event " + event));
    }

    // 6
    @PutMapping("/addfunds")
    public ResponseEntity<?> addFunds(@RequestBody MoneyEntryRequest moneyEntry){
        collectionBoxService.addFunds(moneyEntry);
        return ResponseEntity.ok(Collections.singletonMap("message","Funds added:  " + moneyEntry.getAmount() + " " + moneyEntry.getCurrency()));

    }

    // 7
    @PutMapping("/transfer/{uid}")
    public ResponseEntity<?> transferFunds(@PathVariable String uid){

        collectionBoxService.transfer(uid);
        return ResponseEntity.ok(Collections.singletonMap("message","Money transfered from box: " + uid + " to its event"));
    }

}
