package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.dto.PayableDTO;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.repositories.TransactionRepository;
import com.jvolima.desafiopagarme.utils.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTests {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PayableService payableService;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = Factory.createTransaction();

        Mockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(Factory.createTransaction());
    }

    @Test
    public void processTransactionShouldReturnNothingWhenDataIsValid() {
        Assertions.assertDoesNotThrow(() -> transactionService.processTransaction(Factory.createTransactionDTO()));

        transaction.setId(null);

        Mockito.verify(transactionRepository).save(transaction);
        Mockito.verify(payableService).processPayable(ArgumentMatchers.any(PayableDTO.class));
    }
}
