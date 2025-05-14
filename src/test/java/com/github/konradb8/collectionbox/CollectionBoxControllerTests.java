package com.github.konradb8.collectionbox;

import com.github.konradb8.collectionbox.controller.CollectionBoxController;
import com.github.konradb8.collectionbox.model.box.CollectionBoxListResponse;
import com.github.konradb8.collectionbox.service.CollectionBoxService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CollectionBoxController.class)
@Import(CollectionBoxControllerTests.TestConfig.class)
class CollectionBoxControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CollectionBoxService service;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CollectionBoxService collectionBoxService() {
            return Mockito.mock(CollectionBoxService.class);
        }
    }

    @Test
    void whenCreateBox_thenOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/v1/collection-box")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"uid\":\"BOX1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Collection Box Created"));
        verify(service).registerBox(any());
    }

    @Test
    void whenGetBoxList_thenOk() throws Exception {
        var resp = List.of(new CollectionBoxListResponse("BOX1", false, true));
        when(service.getBoxList()).thenReturn(resp);
        mvc.perform(MockMvcRequestBuilders.get("/v1/collection-box/boxlist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].uid").value("BOX1"));
    }

    @Test
    void whenDeleteBox_thenOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/v1/collection-box/BOX1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Collection Box Deleted"));
        verify(service).deleteBox("BOX1");
    }

    @Test
    void whenAssignBox_thenOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/v1/collection-box/assign/BOX1/EVENT1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Box BOX1 assigned to event EVENT1"));
        verify(service).assignBox("BOX1", "EVENT1");
    }

    @Test
    void whenAddFunds_thenOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/v1/collection-box/addfunds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"boxUid\":\"BOX1\",\"currency\":\"USD\",\"amount\":10}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Funds added:  10 USD"));
        verify(service).addFunds(any());
    }

    @Test
    void whenTransfer_thenOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/v1/collection-box/transfer/BOX1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Money transfered from box: BOX1 to its event"));
        verify(service).transfer("BOX1");
    }
}