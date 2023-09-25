package com.jvolima.desafiopagarme.entities;

import com.jvolima.desafiopagarme.entities.enums.TransactionPaymentMethod;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private TransactionPaymentMethod paymentMethod;

    @Column(nullable = false)
    private Integer cardNumber;

    @Column(nullable = false)
    private String cardholderName;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    private Instant cardExpirationDate;

    @Column(nullable = false)
    private Integer cvv;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToOne(mappedBy = "transaction")
    private Payable payable;

    public Transaction() {
    }

    public Transaction(UUID id, Double value, String description, TransactionPaymentMethod paymentMethod, Integer cardNumber, String cardholderName, Instant cardExpirationDate, Integer cvv, Instant createdAt) {
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

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
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

    public Payable getPayable() {
        return payable;
    }

    public void setPayable(Payable payable) {
        this.payable = payable;
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
