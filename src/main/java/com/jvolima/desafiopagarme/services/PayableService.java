package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.entities.Payable;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.PayableStatus;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class PayableService {

    public void processPayable(Transaction transaction) {
        Payable payable = new Payable();
        TransactionPaymentMethod paymentMethod = transaction.getPaymentMethod();
        double fee = paymentMethod == TransactionPaymentMethod.debit_card ? 0.03 : 0.05;
        payable.setDiscountedValue(transaction.getValue() * (1.0 - fee));
        payable.setStatus(
                paymentMethod == TransactionPaymentMethod.debit_card ?
                        PayableStatus.paid : PayableStatus.waiting_funds
        );
        payable.setPaymentDate(
                paymentMethod == TransactionPaymentMethod.debit_card ?
                        transaction.getCreatedAt() : transaction.getCreatedAt().plus(30, ChronoUnit.DAYS)
        );
        payable.setTransaction(transaction);
    }
}
