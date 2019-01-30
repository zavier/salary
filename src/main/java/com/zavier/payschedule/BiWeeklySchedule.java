package com.zavier.payschedule;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BiWeeklySchedule implements PaymentSchedule {

    private boolean isPayWeek = false; // 每隔一周支付

    @Override
    public boolean isPayDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.FRIDAY && isPayWeek) {
            return true;
        }
        isPayWeek = true;
        return false;
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate date) {
        return date.minusDays(12);
    }
}
