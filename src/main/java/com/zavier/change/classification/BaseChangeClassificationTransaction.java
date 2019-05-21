package com.zavier.change.classification;

import com.zavier.change.BaseChangeEmployeeTransaction;
import com.zavier.classification.BasePaymentClassification;
import com.zavier.employee.Employee;
import com.zavier.payschedule.PaymentSchedule;

/**
 * 修改计薪方式
 */
public abstract class BaseChangeClassificationTransaction extends BaseChangeEmployeeTransaction {

    public BaseChangeClassificationTransaction(int empId) {
        super(empId);
    }

    @Override
    public void change(Employee e) {
        e.setPaymentClassification(getClassification());
        e.setPaymentSchedule(getSchedule());
    }

    protected abstract BasePaymentClassification getClassification();

    protected abstract PaymentSchedule getSchedule();
}
