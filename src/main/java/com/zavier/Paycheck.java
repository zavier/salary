package com.zavier;

import java.time.LocalDate;

public class Paycheck {
    private LocalDate paydate;
    private LocalDate payPeriodStartDate;
    private LocalDate payPeriodEndDate;
    private double grossPay;
    private double deductions;
    private double netPay;

    public LocalDate getPayDate() {
        return paydate;
    }

    public double getGrossPay() {
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

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
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
