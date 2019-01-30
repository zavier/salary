package com.zavier;

import com.zavier.classification.CommissionedClassification;
import com.zavier.classification.PaymentClassification;

import java.time.LocalDate;

public class SalesReceiptTransaction implements Transaction {

    private LocalDate date;
    private double amount;
    private int empid;

    public SalesReceiptTransaction(LocalDate date, double amount, int empid) {
        this.date = date;
        this.amount = amount;
        this.empid = empid;
    }

    @Override
    public void execute() {
        Employee e = GpayrollDatabase.getEmployee(empid);
        if (e != null) {
            PaymentClassification pc = e.getPaymentClassfication();
            if (pc instanceof CommissionedClassification) {
                CommissionedClassification cc = (CommissionedClassification) pc;
                cc.addSalesReceipt(new SalesReceipt(amount, date));
            } else {
                throw new RuntimeException("Tried to add salesReceipt to non-commission employee");
            }
        } else {
            throw new RuntimeException("No Such Employee: " + empid);
        }
    }
}
