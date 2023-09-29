package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.dto.BalanceDTO;
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
import java.util.Date;
import java.util.List;

@Service
public class PayableService {

    @Autowired
    private PayableRepository payableRepository;

    @Transactional(readOnly = true)
    public BalanceDTO getBalance() {
        BalanceDTO balanceDTO = new BalanceDTO();
        List<Payable> paidPayableList = payableRepository.findByStatus(PayableStatus.paid);
        balanceDTO.setAvailable(
                paidPayableList.stream().mapToDouble(Payable::getDiscountedValue).sum()
        );
        List<Payable> waitingFundsPayableList = payableRepository.findByStatus(PayableStatus.waiting_funds);
        balanceDTO.setWaitingFunds(
                waitingFundsPayableList.stream().mapToDouble(Payable::getDiscountedValue).sum()
        );

        return balanceDTO;
    }

    @Transactional
    public void processPayable(Transaction transaction) {
        Payable payable = new Payable();
        TransactionPaymentMethod paymentMethod = transaction.getPaymentMethod();
        payable.setDiscountedValue(calculateDiscountedValue(transaction.getValue(), paymentMethod));
        payable.setStatus(determinePayableStatus(paymentMethod));
        payable.setPaymentDate(Date.from(determinePaymentDate(transaction.getCreatedAt().toInstant(), paymentMethod)));
        payable.setTransaction(transaction);

        payableRepository.save(payable);
    }

    protected double calculateDiscountedValue(Double value, TransactionPaymentMethod paymentMethod) {
        return paymentMethod == TransactionPaymentMethod.debit_card
                ? (value * (1.0 - 0.03)) : (value * (1.0 - 0.05));
    }

    protected PayableStatus determinePayableStatus(TransactionPaymentMethod paymentMethod) {
        return paymentMethod == TransactionPaymentMethod.debit_card
                ? PayableStatus.paid : PayableStatus.waiting_funds;
    }

    protected Instant determinePaymentDate(Instant transactionDate, TransactionPaymentMethod paymentMethod) {
        return paymentMethod == TransactionPaymentMethod.debit_card ?
                transactionDate : transactionDate.plus(30, ChronoUnit.DAYS);
    }
}
