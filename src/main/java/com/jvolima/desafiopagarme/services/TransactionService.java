package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PayableService payableService;

    public void processTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setValue(transactionDTO.getValue());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setPaymentMethod(transactionDTO.getPaymentMethod());
        transaction.setCardNumber(
                Integer.valueOf(transactionDTO.getCardNumber().substring(transactionDTO.getCardNumber().length() - 4))
        );
        transaction.setCardholderName(transactionDTO.getCardholderName());
        transaction.setCardExpirationDate(transactionDTO.getCardExpirationDate());
        transaction.setCvv(transactionDTO.getCvv());
        transaction.setCreatedAt(transactionDTO.getCreatedAt());

        transaction = transactionRepository.save(transaction);
        payableService.processPayable(transaction);
    }
}
