package com.zavier.payschedule;

import java.time.LocalDate;

/**
 * 支付调度
 */
public interface PaymentSchedule {

    /**
     * 是否时支付日期
     * @param date
     * @return
     */
    boolean isPayDate(LocalDate date);

    /**
     * 支付周期的开始日期
     * @param date
     * @return
     */
    LocalDate getPayPeriodStartDate(LocalDate date);
}
