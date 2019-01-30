package com.zavier;

import java.time.LocalDate;

public class ServiceCharge {
    private LocalDate date;
    private double charge;

    public ServiceCharge(LocalDate date, double charge) {
        this.date = date;
        this.charge = charge;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return charge;
    }
}
