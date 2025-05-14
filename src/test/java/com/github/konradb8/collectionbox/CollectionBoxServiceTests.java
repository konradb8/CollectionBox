package com.github.konradb8.collectionbox;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.*;

import com.github.konradb8.collectionbox.model.box.CollectionBox;
import com.github.konradb8.collectionbox.model.box.CollectionBoxRequest;
import com.github.konradb8.collectionbox.model.currency.Currency;
import com.github.konradb8.collectionbox.model.event.FundraisingEvent;
import com.github.konradb8.collectionbox.model.money.MoneyEntry;
import com.github.konradb8.collectionbox.model.money.MoneyEntryRequest;
import com.github.konradb8.collectionbox.repository.CollectionBoxRepository;
import com.github.konradb8.collectionbox.repository.FundraisingEventRepository;
import com.github.konradb8.collectionbox.repository.MoneyEntryRepository;
import com.github.konradb8.collectionbox.service.CollectionBoxService;
import com.github.konradb8.collectionbox.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CollectionBoxServiceTests {

    @Mock
    private CollectionBoxRepository boxRepo;
    @Mock
    private FundraisingEventRepository eventRepo;
    @Mock
    private ExchangeRateService rateService;
    @Mock
    private MoneyEntryRepository entryRepo;

    @InjectMocks
    private CollectionBoxService service;

    private CollectionBoxRequest req;
    private CollectionBox box;

    @BeforeEach
    void setup() {
        req = new CollectionBoxRequest();
        req.setUid("BOX1");
        box = new CollectionBox();
        box.setUid("BOX1");
        box.setMoneyEntryList(new ArrayList<>());
    }

    @Test
    void whenRegisterNewBox_thenSaved() {
        when(boxRepo.existsById("BOX1")).thenReturn(false);
        service.registerBox(req);
        verify(boxRepo).save(argThat(b -> "BOX1".equals(b.getUid()) && b.getMoneyEntryList().isEmpty()));
    }

    @Test
    void whenRegisterDuplicateBox_thenException() {
        when(boxRepo.existsById("BOX1")).thenReturn(true);
        assertThatThrownBy(() -> service.registerBox(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void whenGetBoxListEmpty_thenException() {
        when(boxRepo.findAll()).thenReturn(Collections.emptyList());
        assertThatThrownBy(() -> service.getBoxList())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("list is empty");
    }

    @Test
    void whenGetBoxList_thenReturnResponses() {
        box.setEvent(null);
        when(boxRepo.findAll()).thenReturn(List.of(box));
        var list = service.getBoxList();
        assertThat(list).hasSize(1);
        var resp = list.getFirst();
        assertThat(resp.getUid()).isEqualTo("BOX1");
        assertThat(resp.getIsAssigned()).isFalse();
        assertThat(resp.getIsEmpty()).isTrue();
    }

    @Test
    void whenAddFundsNoBoxUid_thenException() {
        MoneyEntryRequest mreq = new MoneyEntryRequest();
        mreq.setCurrency(Currency.valueOf("USD")); mreq.setAmount(BigDecimal.TEN);
        assertThatThrownBy(() -> service.addFunds(mreq))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not specified");
    }

    @Test
    void whenAddFunds_thenSaved() {
        MoneyEntryRequest mreq = new MoneyEntryRequest();
        mreq.setBoxUid("BOX1"); mreq.setCurrency(Currency.valueOf("USD")); mreq.setAmount(BigDecimal.TEN);
        when(boxRepo.findByUid("BOX1")).thenReturn(box);
        service.addFunds(mreq);
        verify(boxRepo).save(box);
        assertThat(box.getMoneyEntryList()).hasSize(1);
        var entry = box.getMoneyEntryList().getFirst();
        assertThat(entry.getCurrency()).isEqualTo(Currency.valueOf("USD"));
        assertThat(entry.getAmount()).isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    void whenDeleteNonexistent_thenException() {
        when(boxRepo.findByUid("BOX1")).thenReturn(null);
        assertThatThrownBy(() -> service.deleteBox("BOX1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("does not exist");
    }

    @Test
    void whenDeleteBox_thenRepositoriesCalled() {
        MoneyEntry entry = new MoneyEntry();
        box.getMoneyEntryList().add(entry);
        when(boxRepo.findByUid("BOX1")).thenReturn(box);
        service.deleteBox("BOX1");
        verify(boxRepo).delete(box);
        verify(entryRepo).deleteAll(eq(box.getMoneyEntryList()));
    }

    @Test
    void whenAssignAlreadyAssigned_thenException() {
        box.setEvent(new FundraisingEvent());
        when(boxRepo.findByUid("BOX1")).thenReturn(box);
        when(eventRepo.findByName("EVENT1")).thenReturn(new FundraisingEvent());
        assertThatThrownBy(() -> service.assignBox("BOX1", "EVENT1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already assigned");
    }

    @Test
    void whenAssignBox_thenEventSet() {
        when(boxRepo.findByUid("BOX1")).thenReturn(box);
        FundraisingEvent evt = new FundraisingEvent(); evt.setName("EVENT1");
        when(eventRepo.findByName("EVENT1")).thenReturn(evt);
        service.assignBox("BOX1", "EVENT1");
        assertThat(box.getEvent()).isEqualTo(evt);
        verify(boxRepo).save(box);
    }

    @Test
    void whenTransferUnassigned_thenException() {
        box.setEvent(null);
        when(boxRepo.findByUid("BOX1")).thenReturn(box);
        assertThatThrownBy(() -> service.transfer("BOX1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not assigned");
    }

    @Test
    void whenTransfer_thenFundsMovedAndCleared() {
        FundraisingEvent evt = new FundraisingEvent();
        evt.setName("EVENT1"); evt.setCurrency(Currency.valueOf("EUR")); evt.setAmount(BigDecimal.ZERO);
        box.setEvent(evt);
        MoneyEntry e1 = new MoneyEntry(); e1.setCurrency(Currency.valueOf("PLN")); e1.setAmount(new BigDecimal("4")); e1.setBox(box);
        box.getMoneyEntryList().add(e1);
        when(boxRepo.findByUid("BOX1")).thenReturn(box);
        when(eventRepo.findByName("EVENT1")).thenReturn(evt);
        when(rateService.getExchangeRate(Currency.valueOf("PLN"), Currency.valueOf("EUR"))).thenReturn(new BigDecimal("0.25"));

        service.transfer("BOX1");

        assertThat(evt.getAmount()).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(box.getMoneyEntryList()).isEmpty();
        verify(eventRepo).save(evt);
        verify(boxRepo).save(box);
    }
}


