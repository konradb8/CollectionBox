package com.github.konradb8.collectionbox.service;

import com.github.konradb8.collectionbox.model.currency.Currency;
import com.github.konradb8.collectionbox.model.money.MoneyEntry;
import com.github.konradb8.collectionbox.model.money.MoneyEntryRequest;
import com.github.konradb8.collectionbox.model.box.CollectionBox;
import com.github.konradb8.collectionbox.model.box.CollectionBoxListResponse;
import com.github.konradb8.collectionbox.model.box.CollectionBoxRequest;
import com.github.konradb8.collectionbox.model.event.FundraisingEvent;
import com.github.konradb8.collectionbox.repository.CollectionBoxRepository;
import com.github.konradb8.collectionbox.repository.FundraisingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CollectionBoxService {

    private final CollectionBoxRepository collectionBoxRepository;
    private final FundraisingEventService fundraisingEventService;
    private final FundraisingEventRepository fundraisingEventRepository;
    private final ExchangeRateService exchangeRateService;

    public void registerBox(CollectionBoxRequest request) {
        if(collectionBoxRepository.existsById(request.getUid())){
            throw new RuntimeException("Collection box with this UID already exists");
        }

        CollectionBox collectionBox = new CollectionBox();
        collectionBox.setUid(request.getUid());
        collectionBox.setEvent(null);
        collectionBox.setMoneyEntryList(new ArrayList<>());

        collectionBoxRepository.save(collectionBox);

    }

    public List<CollectionBoxListResponse> getBoxList(){
        List<CollectionBox> collectionBoxList = collectionBoxRepository.findAll();
        if(collectionBoxList.isEmpty()){
            throw new RuntimeException("Collection box list is empty");
        }

        return collectionBoxList.stream()
            .map(box -> new CollectionBoxListResponse(
                    box.getUid(),
                    box.getEvent() != null,
                    box.getMoneyEntryList().isEmpty()

            ))
            .toList();
    }

    public void addFunds(MoneyEntryRequest moneyEntry){
        if(moneyEntry.getBoxUid() == null){
            throw new RuntimeException("Box is not specified");
        }
        CollectionBox collectionBox = collectionBoxRepository.findByUid(moneyEntry.getBoxUid());

        MoneyEntry newEntry = new MoneyEntry();
        newEntry.setCurrency(moneyEntry.getCurrency());
        newEntry.setAmount(moneyEntry.getAmount());
        newEntry.setBox(collectionBox);

        collectionBox.getMoneyEntryList().add(newEntry);
        collectionBoxRepository.save(collectionBox);

    }

    public void deleteBox(String uid){
        CollectionBox collectionBox = collectionBoxRepository.findByUid(uid);
        collectionBoxRepository.delete(collectionBox);
    }
    public void assignBox(String uid, String eventName){
        CollectionBox collectionBox = collectionBoxRepository.findByUid(uid);
        FundraisingEvent fundraisingEvent = fundraisingEventRepository.findByName(eventName);

        collectionBox.setEvent(fundraisingEvent);
        collectionBoxRepository.save(collectionBox);
    }

    public void transfer (String uid){
        CollectionBox collectionBox = collectionBoxRepository.findByUid(uid);
        if(collectionBox.getEvent() == null){
            throw new RuntimeException("No event to be transferred");
        }
        FundraisingEvent fundraisingEvent = fundraisingEventRepository.findByName(collectionBox.getEvent().getName());
        Currency currency = fundraisingEvent.getCurrency();

        BigDecimal totalAmount = BigDecimal.ZERO;

        List<MoneyEntry> entries = collectionBox.getMoneyEntryList();

        for(MoneyEntry entry : entries){
            BigDecimal entryAmount = entry.getAmount();
            BigDecimal exchangeRate = exchangeRateService.getExchangeRate(entry.getCurrency(), currency);
            BigDecimal unifiedEntryAmount = entryAmount.multiply(exchangeRate);

            totalAmount = totalAmount.add(unifiedEntryAmount);

        }

        collectionBox.getMoneyEntryList().clear();

        fundraisingEvent.setAmount(fundraisingEvent.getAmount().add(totalAmount));
        fundraisingEventRepository.save(fundraisingEvent);
        collectionBoxRepository.save(collectionBox);

    }







}


