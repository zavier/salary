package com.zavier.classification;


import com.zavier.Paycheck;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 计算薪水的方式(月薪、计时算薪水、底薪+销售提成等)
 */
public abstract class PaymentClassification {
    public abstract BigDecimal calculatePay(Paycheck pc);

    public boolean isInPayPeriod(LocalDate theDate, Paycheck pc) {
        LocalDate payPeriodEndDate = pc.getPayPeriodEndDate();
        LocalDate payPeriodStartDate = pc.getPayPeriodStartDate();
        return theDate.compareTo(payPeriodStartDate) >= 0 && theDate.compareTo(payPeriodEndDate) <= 0;
    }
}
