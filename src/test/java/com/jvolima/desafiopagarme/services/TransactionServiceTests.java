package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.repositories.TransactionRepository;
import com.jvolima.desafiopagarme.services.exceptions.UnprocessableEntityException;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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

        Mockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(transaction);

        Mockito.doNothing().when(payableService).processPayable(transaction);
    }

    @Test
    public void processTransactionShouldReturnNothingWhenDataIsValid() {
        Assertions.assertDoesNotThrow(() -> transactionService.processTransaction(Factory.createTransactionDTO()));

        Mockito.verify(payableService).processPayable(transaction);

        transaction.setId(null);
        Mockito.verify(transactionRepository).save(transaction);
    }

    @Test
    public void processTransactionShouldThrowUnprocessableEntityExceptionWhenCardExpirationDateIsInvalid() {
        Assertions.assertThrows(UnprocessableEntityException.class, () -> {
            TransactionDTO transactionDTO = Factory.createTransactionDTO();
            transactionDTO.setCardExpirationDate(Instant.now().minus(365, ChronoUnit.DAYS));

            transactionService.processTransaction(transactionDTO);
        });

        Mockito.verifyNoInteractions(transactionRepository);
        Mockito.verifyNoInteractions(payableService);
    }
}
