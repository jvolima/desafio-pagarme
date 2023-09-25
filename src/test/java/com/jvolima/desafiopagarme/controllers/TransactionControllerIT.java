package com.jvolima.desafiopagarme.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.utils.Factory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
public class TransactionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listTransactionsShouldReturnATransactionsDTOPage() throws Exception {
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    @Test
    public void processTransactionShouldReturnCreatedWhenDataIsValid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.createTransactionDTO());

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void processTransactionShouldReturnUnprocessableEntityWhenValueIsLessThanOrEqualToZero() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setValue(-100.0);
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

        transactionDTO.setValue(0.0);
        jsonBody = objectMapper.writeValueAsString(transactionDTO);

        result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
}
