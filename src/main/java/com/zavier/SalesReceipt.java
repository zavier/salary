package com.zavier;

import java.time.LocalDate;

public class SalesReceipt {
    private double amount;
    private LocalDate date;

    public SalesReceipt(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
