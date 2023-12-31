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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
        PageImpl<Transaction> transactionsPage = new PageImpl<>(List.of(transaction));

        Mockito.when(transactionRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(transactionsPage);

        Mockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(transaction);

        Mockito.doNothing().when(payableService).processPayable(transaction);
    }

    @Test
    public void listTransactionsShouldReturnATransactionsDTOPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<TransactionDTO> transactionsDTOPage = transactionService.listTransactions(pageable);

        Assertions.assertEquals(1, transactionsDTOPage.getSize());
        Mockito.verify(transactionRepository).findAll(pageable);
    }


    @Test
    public void processTransactionShouldReturnTransactionDTOWhenDataIsValid() {
        TransactionDTO transactionDTO = Factory.createTransactionDTO();
        TransactionDTO createdDTO = transactionService.processTransaction(transactionDTO);

        Assertions.assertNotNull(createdDTO.getId());
        Assertions.assertNotNull(createdDTO.getCreatedAt());
        Assertions.assertEquals(transactionDTO.getCardExpirationDate(), createdDTO.getCardExpirationDate());
        Mockito.verify(payableService).processPayable(transaction);

        transaction.setId(null);
        Mockito.verify(transactionRepository).save(transaction);
    }

    @Test
    public void processTransactionShouldThrowUnprocessableEntityExceptionWhenCardExpirationDateIsInvalid() {
        Assertions.assertThrows(UnprocessableEntityException.class, () -> {
            TransactionDTO transactionDTO = Factory.createTransactionDTO();
            Instant instant = Instant.now().minus(365, ChronoUnit.DAYS);
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
            String month = String.format("%02d", zonedDateTime.getMonthValue());
            int year = zonedDateTime.getYear();
            String cardExpirationDate = month + "/" + year;
            transactionDTO.setCardExpirationDate(cardExpirationDate);

            transactionService.processTransaction(transactionDTO);
        });

        Mockito.verifyNoInteractions(transactionRepository);
        Mockito.verifyNoInteractions(payableService);
    }

    @Test
    public void getTheLast4DigitsOfTheCardNumberShouldReturnTheLast4Digits() {
        String cardNumber = "8421098574156972";
        String last4Digits = transactionService.getTheLast4DigitsOfTheCardNumber(cardNumber);

        Assertions.assertEquals("6972", last4Digits);
    }
}
