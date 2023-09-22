package com.jvolima.desafiopagarme.services;

import com.jvolima.desafiopagarme.entities.Payable;
import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.PayableStatus;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import com.jvolima.desafiopagarme.repositories.PayableRepository;
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

@ExtendWith(SpringExtension.class)
public class PayableServiceTests {

    @InjectMocks
    private PayableService payableService;

    @Mock
    private PayableRepository payableRepository;

    private Payable payable;

    @BeforeEach
    public void setUp() {
        payable = Factory.createPayable();

        Mockito.when(payableRepository.save(ArgumentMatchers.any(Payable.class))).thenReturn(payable);
    }

    @Test
    public void processPayableShouldReturnNothing() {
        Assertions.assertDoesNotThrow(() -> payableService.processPayable(Factory.createTransaction()));

        payable.setId(null);
        Mockito.verify(payableRepository).save(payable);
    }

    @Test
    public void calculateDiscountedValueShouldReturnValueWith3PercentFeeWhenPaymentMethodIsDebitCard() {
        TransactionPaymentMethod paymentMethod = TransactionPaymentMethod.debit_card;
        double value = 1000.0;

        double discountedValue = payableService.calculateDiscountedValue(value, paymentMethod);

        Assertions.assertEquals(970.0, discountedValue);
    }

    @Test
    public void calculateDiscountedValueShouldReturnValueWith5PercentFeeWhenPaymentMethodIsCreditCard() {
        TransactionPaymentMethod paymentMethod = TransactionPaymentMethod.credit_card;
        double value = 1000.0;

        double discountedValue = payableService.calculateDiscountedValue(value, paymentMethod);

        Assertions.assertEquals(950.0, discountedValue);
    }

    @Test
    public void determinePayableStatusShouldReturnPaidWhenPaymentMethodIsDebitCard() {
        TransactionPaymentMethod paymentMethod = TransactionPaymentMethod.debit_card;

        PayableStatus payableStatus = payableService.determinePayableStatus(paymentMethod);

        Assertions.assertEquals(PayableStatus.paid, payableStatus);
    }

    @Test
    public void determinePayableStatusShouldReturnWaitingFundsWhenPaymentMethodIsCreditCard() {
        TransactionPaymentMethod paymentMethod = TransactionPaymentMethod.credit_card;

        PayableStatus payableStatus = payableService.determinePayableStatus(paymentMethod);

        Assertions.assertEquals(PayableStatus.waiting_funds, payableStatus);
    }

    @Test
    public void determinePaymentDateShouldReturnDPlusZeroWhenPaymentMethodIsDebitCard() {
        Transaction transaction = Factory.createTransaction();
        TransactionPaymentMethod paymentMethod = TransactionPaymentMethod.debit_card;

        Instant paymentDate = payableService.determinePaymentDate(transaction.getCreatedAt(), paymentMethod);

        Assertions.assertEquals(transaction.getCreatedAt(), paymentDate);
    }
}
