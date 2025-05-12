package com.github.konradb8.collectionbox.controller;

import com.github.konradb8.collectionbox.model.event.FundraisingEvent;
import com.github.konradb8.collectionbox.model.event.FundraisingEventReportResponse;
import com.github.konradb8.collectionbox.model.event.FundraisingEventRequest;
import com.github.konradb8.collectionbox.service.FundraisingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/event")
@RequiredArgsConstructor
public class FundraisingEventController {

    private final FundraisingEventService fundraisingEventService;

    @PostMapping
    public ResponseEntity<FundraisingEvent> createFundraisingEvent(@RequestBody FundraisingEventRequest request) {
        FundraisingEvent createdEvent = fundraisingEventService.createFundraisingEvent(request);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }
    @GetMapping("/report")
    public ResponseEntity<List<FundraisingEventReportResponse>> getReport() {
        return ResponseEntity.ok(fundraisingEventService.getReport());

    }
}
