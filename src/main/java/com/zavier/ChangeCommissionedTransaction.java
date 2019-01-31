package com.zavier;

import com.zavier.classification.CommissionedClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.BiWeeklySchedule;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {

    private BigDecimal salary;
    private BigDecimal commissionRate;

    public ChangeCommissionedTransaction(int empId, BigDecimal salary, BigDecimal commissionRate) {
        super(empId);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiWeeklySchedule();
    }
}
