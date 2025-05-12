package com.github.konradb8.collectionbox.service;

import com.github.konradb8.collectionbox.model.Currency;
import com.github.konradb8.collectionbox.model.event.FundraisingEvent;
import com.github.konradb8.collectionbox.model.event.FundraisingEventReportResponse;
import com.github.konradb8.collectionbox.model.event.FundraisingEventRequest;
import com.github.konradb8.collectionbox.repository.FundraisingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundraisingEventService {

    private final FundraisingEventRepository fundraisingEventRepository;

    public FundraisingEvent createFundraisingEvent(FundraisingEventRequest request) {

        Currency currency = Currency.valueOf(request.getCurrency());

        FundraisingEvent event = new FundraisingEvent();
        event.setName(request.getName());
        event.setCurrency(currency);
        event.setAmount(BigDecimal.ZERO);

        return fundraisingEventRepository.save(event);
    }
















    public List<FundraisingEventReportResponse> getReport(){
        List<FundraisingEvent> events = fundraisingEventRepository.findAll();
        return events.stream()
                .map(event -> new FundraisingEventReportResponse(
                        event.getName(),
                        event.getAmount(),
                        event.getCurrency()
                ))
                .toList();

    }
}
