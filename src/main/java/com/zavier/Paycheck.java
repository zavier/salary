package com.zavier;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Paycheck {
    private LocalDate paydate;
    private LocalDate payPeriodStartDate;
    private LocalDate payPeriodEndDate;
    private BigDecimal grossPay;
    private BigDecimal deductions;
    private BigDecimal netPay;

    public LocalDate getPayDate() {
        return paydate;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public LocalDate getPayPeriodStartDate() {
        return payPeriodStartDate;
    }

    public void setPayPeriodStartDate(LocalDate payPeriodStartDate) {
        this.payPeriodStartDate = payPeriodStartDate;
    }

    public LocalDate getPayPeriodEndDate() {
        return payPeriodEndDate;
    }

    public void setPayPeriodEndDate(LocalDate payPeriodEndDate) {
        this.payPeriodEndDate = payPeriodEndDate;
    }

    public void setGrossPay(BigDecimal grossPay) {
        this.grossPay = grossPay;
    }

    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    public BigDecimal getNetPay() {
        return netPay;
    }

    public void setNetPay(BigDecimal netPay) {
        this.netPay = netPay;
    }

    public Paycheck(LocalDate payPeriodStartDate, LocalDate paydate) {
        this.paydate = paydate;
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = paydate;
    }

    @Override
    public String toString() {
        return "Paycheck{" +
            "grossPay=" + grossPay +
            ", deductions=" + deductions +
            ", netPay=" + netPay +
            '}';
    }
}
