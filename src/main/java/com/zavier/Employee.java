package com.zavier;


import com.zavier.affiliation.Affiliation;
import com.zavier.classification.PaymentClassification;
import com.zavier.paymethod.PaymentMethod;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private int id;
    private String name;
    private String address;
    private PaymentSchedule paymentSchedule;
    private PaymentMethod paymentMethod;
    private PaymentClassification paymentClassfication;
    private Affiliation affiliation;

    public Employee(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public PaymentClassification getPaymentClassfication() {
        return paymentClassfication;
    }

    public void setPaymentClassfication(
        PaymentClassification paymentClassfication) {
        this.paymentClassfication = paymentClassfication;
    }

    public void setAffilication(Affiliation affiliations) {
        this.affiliation = affiliations;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public boolean isPayDate(LocalDate date) {
        return paymentSchedule.isPayDate(date);
    }

    public LocalDate getPayPeriodStartDate(LocalDate payDate) {
        return paymentSchedule.getPayPeriodStartDate(payDate);
    }

    public void payday(Paycheck pc) {
        BigDecimal grossPay = paymentClassfication.calculatePay(pc);
        BigDecimal deductions = affiliation.calculateDeductions(pc);
        BigDecimal netPay = grossPay.subtract(deductions);
        pc.setGrossPay(grossPay);
        pc.setDeductions(deductions);
        pc.setNetPay(netPay);
        paymentMethod.pay(pc);
    }
}
