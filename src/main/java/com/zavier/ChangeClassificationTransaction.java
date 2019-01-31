package com.zavier;

import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.PaymentSchedule;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

    public ChangeClassificationTransaction(int empId) {
        super(empId);
    }

    protected abstract PaymentClassification getClassification();
    protected abstract PaymentSchedule getSchedule();

    @Override
    public void change(Employee e) {
        e.setPaymentClassfication(getClassification());
        e.setPaymentSchedule(getSchedule());
    }
}
