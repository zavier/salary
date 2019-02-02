package com.zavier.classification;

import com.zavier.pay.Paycheck;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 计算薪水的方式
 */
public interface PaymentClassification {
    /**
     * 计算薪水
     * @param pc
     * @return
     */
    BigDecimal calculatePay(Paycheck pc);

    /**
     * 判断是否在结算日期
     * @param theDate
     * @param pc
     * @return
     */
    boolean isInPayPeriod(LocalDate theDate, Paycheck pc);
}
