package com.jvolima.desafiopagarme.dto;

public class BalanceDTO {

    private Double available;
    private Double waitingFunds;

    public BalanceDTO() {
    }

    public BalanceDTO(Double available, Double waitingFunds) {
        this.available = available;
        this.waitingFunds = waitingFunds;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public Double getWaitingFunds() {
        return waitingFunds;
    }

    public void setWaitingFunds(Double waitingFunds) {
        this.waitingFunds = waitingFunds;
    }
}
