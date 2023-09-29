package com.jvolima.desafiopagarme.controllers;

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
public class PayableControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBalanceShouldReturnBalanceDTO() throws Exception {
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/payables/balance")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.available").value(840 * 0.97));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.waitingFunds").value(250 * 0.95));
    }
}
