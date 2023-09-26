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
    public void processTransactionShouldReturnUnprocessableEntityWhenValueIsNull() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setValue(null);
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
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

    @Test
    public void processTransactionShouldReturnUnprocessableEntityWhenDescriptionIsBlank() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setDescription(null);
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void processTransactionShouldReturnUnprocessableEntityWhenDescriptionHasMoreThan255Characters() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setDescription("Lorem ipsum dolor sit amet. Aut eligendi repellat aut nemo consequatur 33 rerum accusantium est mollitia pariatur a obcaecati sint quo temporibus doloremque. Non quis praesentium et veniam dolor in quos minus sit dolores obcaecati non nihil quae. Et omnis molestiae in modi delectus ab asperiores dignissimos.");
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void processTransactionShouldReturnUnprocessableEntityWhenPaymentMethodIsBlank() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setPaymentMethod(null);
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void processTransactionShouldReturnUnprocessableEntityWhenPaymentMethodIsNeitherDebitCardNorCreditCard() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setPaymentMethod("non_valid_card");
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void processTransactionShouldReturnUnprocessableEntityWhenCardNumberIsBlank() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setCardNumber(null);
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void processTransactionShouldReturnUnprocessableEntityWhenCardNumberIsOutOfDesiredFormat() throws Exception {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        transactionDTO.setCardNumber("38743698");
        String jsonBody = objectMapper.writeValueAsString(transactionDTO);

        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
}
