package com.zavier.employee;


import com.zavier.pay.Paycheck;
import com.zavier.affiliation.Affiliation;
import com.zavier.classification.PaymentClassification;
import com.zavier.paymethod.PaymentMethod;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private Integer id;
    private String name;
    private String address;
    private PaymentSchedule paymentSchedule;
    private PaymentMethod paymentMethod;
    private PaymentClassification paymentClassification;
    private Affiliation affiliation;

    public Employee(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentClassification getPaymentClassification() {
        return paymentClassification;
    }

    public void setPaymentClassification(
        PaymentClassification paymentClassification) {
        this.paymentClassification = paymentClassification;
    }

    public void setAffiliation(Affiliation affiliations) {
        this.affiliation = affiliations;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    /**
     * 判断是否是此员工的发薪日期
     * @param date
     * @return
     */
    public boolean isPayDate(LocalDate date) {
        return paymentSchedule.isPayDate(date);
    }

    /**
     * 获取支付周期的开始日期
     * @param payDate
     * @return
     */
    public LocalDate getPayPeriodStartDate(LocalDate payDate) {
        return paymentSchedule.getPayPeriodStartDate(payDate);
    }

    /**
     * 根据参数中的时间计算并支付薪水
     * @param pc
     */
    public void payday(Paycheck pc) {
        BigDecimal grossPay = paymentClassification.calculatePay(pc);
        BigDecimal deductions = affiliation.calculateDeductions(pc);
        BigDecimal netPay = grossPay.subtract(deductions);
        pc.setGrossPay(grossPay);
        pc.setDeductions(deductions);
        pc.setNetPay(netPay);
        // 计算金额后通过选中的支付方式进行支付
        paymentMethod.pay(pc);
    }
}
