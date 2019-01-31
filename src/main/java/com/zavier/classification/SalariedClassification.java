package com.zavier.classification;

import com.zavier.Paycheck;

import java.math.BigDecimal;

/**
 * 月薪方式结薪
 */
public class SalariedClassification extends PaymentClassification {
    /**
     * 每月薪水
     */
    private BigDecimal salary;

    public SalariedClassification(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public BigDecimal calculatePay(Paycheck pc) {
        return salary;
    }
}
