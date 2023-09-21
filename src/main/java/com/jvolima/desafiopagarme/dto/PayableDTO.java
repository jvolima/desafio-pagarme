package com.jvolima.desafiopagarme.dto;

import com.jvolima.desafiopagarme.entities.Payable;
import com.jvolima.desafiopagarme.entities.enums.PayableStatus;

import java.time.Instant;
import java.util.UUID;

public class PayableDTO {

    private UUID id;
    private PayableStatus status;
    private Instant paymentDate;
    private Double discountedValue;
    private UUID transactionId;

    public PayableDTO() {
    }

    public PayableDTO(UUID id, PayableStatus status, Instant paymentDate, Double discountedValue, UUID transactionId) {
        this.id = id;
        this.status = status;
        this.paymentDate = paymentDate;
        this.discountedValue = discountedValue;
        this.transactionId = transactionId;
    }

    public PayableDTO(Payable entity) {
        id = entity.getId();
        status = entity.getStatus();
        paymentDate = entity.getPaymentDate();
        discountedValue = entity.getDiscountedValue();
        transactionId = entity.getTransaction().getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PayableStatus getStatus() {
        return status;
    }

    public void setStatus(PayableStatus status) {
        this.status = status;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getDiscountedValue() {
        return discountedValue;
    }

    public void setDiscountedValue(Double discountedValue) {
        this.discountedValue = discountedValue;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
