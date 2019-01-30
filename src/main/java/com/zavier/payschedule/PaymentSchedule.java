package com.zavier.payschedule;

import java.time.LocalDate;

public interface PaymentSchedule {
    boolean isPayDate(LocalDate date);
    LocalDate getPayPeriodStartDate(LocalDate date);
}