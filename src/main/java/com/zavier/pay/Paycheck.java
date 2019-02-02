package com.zavier.pay;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 用于保存支付时间相关信息
 */
public class Paycheck {
    /**
     * 发薪日期
     */
    private LocalDate payDate;

    /**
     * 计算薪水-开始日期
     */
    private LocalDate payPeriodStartDate;

    /**
     * 计算薪水-结束日期
     */
    private LocalDate payPeriodEndDate;

    /**
     * 应付金额
     */
    private BigDecimal grossPay;

    /**
     * 扣减金额
     */
    private BigDecimal deductions;

    /**
     * 实付金额
     */
    private BigDecimal netPay;

    /**
     * 构造函数
     * @param payPeriodStartDate
     * @param payDate
     */
    public Paycheck(LocalDate payPeriodStartDate, LocalDate payDate) {
        this.payDate = payDate;
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = payDate;
    }

    public LocalDate getPayDate() {
        return payDate;
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

    @Override
    public String toString() {
        return "Paycheck{" +
            "grossPay=" + grossPay +
            ", deductions=" + deductions +
            ", netPay=" + netPay +
            '}';
    }
}
