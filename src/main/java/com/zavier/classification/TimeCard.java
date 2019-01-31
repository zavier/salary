package com.zavier.classification;

import java.time.LocalDate;

/**
 * 工时记录卡
 */
public class TimeCard {
    private double itsHours;
    private LocalDate data;

    public TimeCard(LocalDate data, double itsHours) {
        this.data = data;
        this.itsHours = itsHours;
    }

    public double getHours() {
        return itsHours;
    }

    public LocalDate getDate() {
        return data;
    }
}
