package com.zavier.payschedule;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class MonthlySchedule implements PaymentSchedule {

    public boolean isLastDayOfMonth(LocalDate date) {
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        return date.equals(lastDayOfMonth);
    }

    @Override
    public boolean isPayDate(LocalDate date) {
        return isLastDayOfMonth(date);
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonthValue() - 1, 1);
    }
}
