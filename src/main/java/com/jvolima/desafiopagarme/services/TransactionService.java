package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.repositories.TransactionRepository;
import com.jvolima.desafiopagarme.services.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PayableService payableService;

    @Transactional(readOnly = true)
    public Page<TransactionDTO> listTransactions(Pageable pageable) {
        Page<Transaction> transactionsPage = transactionRepository.findAll(pageable);

        return transactionsPage.map(TransactionDTO::new);
    }

    @Transactional
    public void processTransaction(TransactionDTO transactionDTO) {

        if (Instant.now().isAfter(transactionDTO.getCardExpirationDate())) {
            throw new UnprocessableEntityException("Invalid card expiration date.");
        }

        Transaction transaction = new Transaction();
        transaction.setValue(transactionDTO.getValue());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setPaymentMethod(transactionDTO.getPaymentMethod());
        transaction.setCardNumber(getTheLast4DigitsOfTheCardNumber(transactionDTO.getCardNumber()));
        transaction.setCardholderName(transactionDTO.getCardholderName());
        transaction.setCardExpirationDate(transactionDTO.getCardExpirationDate());
        transaction.setCvv(transactionDTO.getCvv());

        transaction = transactionRepository.save(transaction);
        payableService.processPayable(transaction);
    }

    public Integer getTheLast4DigitsOfTheCardNumber(String cardNumber) {
        return Integer.valueOf(cardNumber.substring(cardNumber.length() - 4));
    }
}
