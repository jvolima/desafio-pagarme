package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTests {

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void processTransactionShouldReturnNothingWhenDataIsValid() {
        Assertions.assertDoesNotThrow(() -> transactionService.processTransaction(Factory.createTransactionDTO()));
    }
}
