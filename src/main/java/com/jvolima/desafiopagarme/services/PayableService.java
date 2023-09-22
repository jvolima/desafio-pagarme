package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.entities.Payable;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.PayableStatus;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import com.jvolima.desafiopagarme.repositories.PayableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class PayableService {

    @Autowired
    private PayableRepository payableRepository;

    @Transactional
    public void processPayable(Transaction transaction) {
        Payable payable = new Payable();
        TransactionPaymentMethod paymentMethod = transaction.getPaymentMethod();
        payable.setDiscountedValue(calculateDiscountedValue(transaction.getValue(), paymentMethod));
        payable.setStatus(determinePayableStatus(paymentMethod));
        payable.setPaymentDate(determinePaymentDate(transaction.getCreatedAt(), paymentMethod));
        payable.setTransaction(transaction);

        payableRepository.save(payable);
    }

    public double calculateDiscountedValue(Double value, TransactionPaymentMethod paymentMethod) {
        return paymentMethod == TransactionPaymentMethod.debit_card
                ? (value * (1.0 - 0.03)) : (value * (1.0 - 0.05));
    }

    public PayableStatus determinePayableStatus(TransactionPaymentMethod paymentMethod) {
        return paymentMethod == TransactionPaymentMethod.debit_card
                ? PayableStatus.paid : PayableStatus.waiting_funds;
    }

    public Instant determinePaymentDate(Instant transactionDate, TransactionPaymentMethod paymentMethod) {
        return paymentMethod == TransactionPaymentMethod.debit_card ?
                transactionDate : transactionDate.plus(30, ChronoUnit.DAYS);
    }
}
