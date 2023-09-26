package com.jvolima.desafiopagarme.dto;

import com.jvolima.desafiopagarme.entities.Transaction;
import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.UUID;

public class TransactionDTO {

    private UUID id;

    @NotNull(message = "Value is required.")
    @DecimalMin(value = "0", inclusive = false, message = "Value should be greater than or equal to zero.")
    private Double value;

    @NotBlank(message = "Description is required.")
    @Size(max = 255, message = "Description should have no more than 255 characters.")
    private String description;

    @NotBlank(message = "Payment method is required.")
    @Pattern(regexp = "^(debit_card|credit_card)?$", message = "Payment method should be debit_card or credit_card")
    private String paymentMethod;

    @NotBlank(message = "Card number is required.")
    @Pattern(regexp = "\\d{4} \\d{4} \\d{4} \\d{4}", message = "Card number is out of desired format, expected format: 0000 0000 0000 0000")
    private String cardNumber;

    @NotBlank(message = "Cardholder name is required.")
    private String cardholderName;
    private Instant cardExpirationDate;
    private Integer cvv;
    private Instant createdAt;

    public TransactionDTO() {
    }

    public TransactionDTO(UUID id, Double value, String description, String paymentMethod, String cardNumber, String cardholderName, Instant cardExpirationDate, Integer cvv, Instant createdAt) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.cardExpirationDate = cardExpirationDate;
        this.cvv = cvv;
        this.createdAt = createdAt;
    }

    public TransactionDTO(Transaction entity) {
        id = entity.getId();
        value = entity.getValue();
        description = entity.getDescription();
        paymentMethod = entity.getPaymentMethod().name();
        cardNumber = String.valueOf(entity.getCardNumber());
        cardholderName = entity.getCardholderName();
        cardExpirationDate = entity.getCardExpirationDate();
        cvv = entity.getCvv();
        createdAt = entity.getCreatedAt();
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
