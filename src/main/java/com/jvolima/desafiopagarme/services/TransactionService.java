package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.dto.PayableDTO;
import com.jvolima.desafiopagarme.dto.TransactionDTO;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.PayableStatus;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import com.jvolima.desafiopagarme.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

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

        PayableDTO payableDTO = new PayableDTO();
        TransactionPaymentMethod paymentMethod = transaction.getPaymentMethod();
        double fee = paymentMethod == TransactionPaymentMethod.debit_card ? 0.03 : 0.05;
        payableDTO.setDiscountedValue(transaction.getValue() * (1.0 - fee));
        payableDTO.setStatus(
                paymentMethod == TransactionPaymentMethod.debit_card ?
                        PayableStatus.paid : PayableStatus.waiting_funds
        );
        payableDTO.setPaymentDate(
                paymentMethod == TransactionPaymentMethod.debit_card ?
                        transaction.getCreatedAt() : transaction.getCreatedAt().plus(30, ChronoUnit.DAYS)
        );
        payableDTO.setTransactionId(transaction.getId());

        payableService.processPayable(payableDTO);
    }
}
