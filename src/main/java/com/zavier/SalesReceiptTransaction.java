package com.zavier;

import com.zavier.classification.CommissionedClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.classification.SalesReceipt;
import com.zavier.employee.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 添加销售凭条
 */
public class SalesReceiptTransaction implements Transaction {
    /**
     * 销售日期
     */
    private LocalDate date;

    /**
     * 销售金额
     */
    private BigDecimal amount;

    /**
     * 雇员ID
     */
    private int empId;

    public SalesReceiptTransaction(LocalDate date, BigDecimal amount, int empId) {
        this.date = date;
        this.amount = amount;
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = GpayrollDatabase.getEmployee(empId);
        if (e != null) {
            PaymentClassification pc = e.getPaymentClassfication();
            if (pc instanceof CommissionedClassification) {
                CommissionedClassification cc = (CommissionedClassification) pc;
                cc.addSalesReceipt(new SalesReceipt(amount, date));
            } else {
                throw new RuntimeException("Tried to add salesReceipt to non-commission employee");
            }
        } else {
            throw new RuntimeException("No Such Employee: " + empId);
        }
    }
}
