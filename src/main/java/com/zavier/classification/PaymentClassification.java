package com.zavier.classification;


import com.zavier.Paycheck;

import java.time.LocalDate;

public abstract class PaymentClassification {
    public abstract double calculatePay(Paycheck pc);

    public boolean isInPayPeriod(LocalDate theDate, Paycheck pc) {
        LocalDate payPeriodEndDate = pc.getPayPeriodEndDate();
        LocalDate payPeriodStartDate = pc.getPayPeriodStartDate();
        return theDate.compareTo(payPeriodStartDate) >= 0 && theDate.compareTo(payPeriodEndDate) <= 0;
    }
}
