package com.jvolima.desafiopagarme.entities;

import com.jvolima.desafiopagarme.entities.enums.PayableStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "payables")
public class Payable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private PayableStatus status;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    private Instant paymentDate;

    @Column(nullable = false)
    private Double discountedValue;

    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

    public Payable() {
    }

    public Payable(UUID id, PayableStatus status, Instant paymentDate, Double discountedValue, Transaction transaction) {
        this.id = id;
        this.status = status;
        this.paymentDate = paymentDate;
        this.discountedValue = discountedValue;
        this.transaction = transaction;
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payable payable = (Payable) o;
        return Objects.equals(id, payable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
