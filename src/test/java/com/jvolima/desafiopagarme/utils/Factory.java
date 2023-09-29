package com.jvolima.desafiopagarme.utils;

import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.entities.Payable;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.PayableStatus;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class Factory {

    public static Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setValue(100.0);
        transaction.setDescription("Compra para testes");
        transaction.setPaymentMethod(TransactionPaymentMethod.debit_card);
        transaction.setCardNumber("1234");
        transaction.setCardholderName("Comprador teste");
        transaction.setCardExpirationDate(Date.from(Instant.now().plus(365 * 3, ChronoUnit.DAYS)));
        transaction.setCvv(321);
        transaction.setCreatedAt(Date.from(Instant.now()));

        return transaction;
    }

    public static TransactionDTO createTransactionDTO() {
        TransactionDTO transactionDTO = new TransactionDTO(createTransaction());
        transactionDTO.setId(null);
        transactionDTO.setCreatedAt(null);
        transactionDTO.setCardNumber("1234 1234 1234 1234");

        return transactionDTO;
    }

    public static Payable createPayable() {
        Payable payable = new Payable();
        payable.setId(UUID.randomUUID());
        payable.setDiscountedValue(97.0);
        payable.setStatus(PayableStatus.paid);
        payable.setPaymentDate(Date.from(Instant.now()));
        payable.setTransaction(createTransaction());

        return payable;
    }
}
