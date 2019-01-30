package com.zavier.payschedule;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklySchedule implements PaymentSchedule {

    @Override
    public boolean isPayDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY;
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate data) {
        return data.minusDays(5);
    }
}
