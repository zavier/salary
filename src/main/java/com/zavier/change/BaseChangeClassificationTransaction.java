package com.zavier.change;

import com.zavier.employee.Employee;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.PaymentSchedule;

public abstract class BaseChangeClassificationTransaction extends BaseChangeEmployeeTransaction {

    public BaseChangeClassificationTransaction(int empId) {
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
