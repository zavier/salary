package com.zavier;

import com.zavier.classification.CommissionedClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.BiWeeklySchedule;
import com.zavier.payschedule.PaymentSchedule;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {

    private double salary;
    private double commissionRate;

    public ChangeCommissionedTransaction(int empId, double salary, double commissionRate) {
        super(empId);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification getClassificiation() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiWeeklySchedule();
    }
}
