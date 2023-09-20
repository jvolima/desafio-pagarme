package com.jvolima.desafiopagarme.dto;

import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;

import java.time.Instant;
import java.util.UUID;

public class TransactionDTO {

    private UUID id;
    private Double value;
    private String description;
    private TransactionPaymentMethod paymentMethod;
    private String cardNumber;
    private String cardholderName;
    private Instant cardExpirationDate;
    private Integer cvv;

    public TransactionDTO() {
    }

    public TransactionDTO(UUID id, Double value, String description, TransactionPaymentMethod paymentMethod, String cardNumber, String cardholderName, Instant cardExpirationDate, Integer cvv) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.cardExpirationDate = cardExpirationDate;
        this.cvv = cvv;
    }

    public TransactionDTO(Transaction entity) {
        id = entity.getId();
        value = entity.getValue();
        description = entity.getDescription();
        paymentMethod = entity.getPaymentMethod();
        cardNumber = String.valueOf(entity.getCardNumber());
        cardholderName = entity.getCardholderName();
        cardExpirationDate = entity.getCardExpirationDate();
        cvv = entity.getCvv();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(TransactionPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public Instant getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(Instant cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
}
