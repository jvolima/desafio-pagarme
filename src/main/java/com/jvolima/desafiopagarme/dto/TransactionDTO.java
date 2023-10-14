package com.jvolima.desafiopagarme.dto;

import com.jvolima.desafiopagarme.entities.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.UUID;

public class TransactionDTO {

    private UUID id;

    @Schema(description = "value", type = "double", example = "100.0")
    @NotNull(message = "Value is required.")
    @DecimalMin(value = "0", inclusive = false, message = "Value should be greater than or equal to zero.")
    private Double value;

    @Schema(description = "description", type = "string", example = "example of description")
    @NotBlank(message = "Description is required.")
    @Size(max = 255, message = "Description should have no more than 255 characters.")
    private String description;

    @Schema(description = "paymentMethod", type = "string", example = "credit_card")
    @NotBlank(message = "Payment method is required.")
    @Pattern(regexp = "^(debit_card|credit_card)?$", message = "Payment method should be debit_card or credit_card")
    private String paymentMethod;

    @Schema(description = "cardNumber", type = "string", example = "5485 9552 4845 3189")
    @NotBlank(message = "Card number is required.")
    @Pattern(regexp = "\\d{4} \\d{4} \\d{4} \\d{4}", message = "Card number is out of desired format, expected format: 0000 0000 0000 0000")
    private String cardNumber;

    @Schema(description = "cardholderName", type = "string", example = "John Doe")
    @NotBlank(message = "Cardholder name is required.")
    @Size(max = 255, message = "Cardholder name should have no more than 255 characters.")
    private String cardholderName;

    @Schema(description = "cardExpirationDate", type = "string", example = "10/2028")
    @NotBlank(message = "Card expiration date is required.")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{4}$", message = "Card expiration date is out of desired format, expected format: MM/YYYY")
    private String cardExpirationDate;

    @Schema(description = "cvv", type = "string", example = "954")
    @NotNull(message = "CVV is required.")
    @Pattern(regexp = "\\d{3}", message = "CVV is out of desired format, expected format: 000")
    private String cvv;

    @Schema(hidden = true)
    private Date createdAt;

    public TransactionDTO() {
    }

    public TransactionDTO(UUID id, Double value, String description, String paymentMethod, String cardNumber, String cardholderName, String cardExpirationDate, String cvv, Date createdAt) {
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
        String month = String.format("%02d", entity.getCardExpirationDate().getMonth() + 1);
        int year = entity.getCardExpirationDate().getYear() + 1900;
        cardExpirationDate = month + "/" + year;
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

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
