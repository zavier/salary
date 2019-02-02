package com.zavier.change;

import com.zavier.employee.Employee;
import com.zavier.classification.BasePaymentClassification;
import com.zavier.payschedule.PaymentSchedule;

/**
 * 修改计薪方式
 */
public abstract class BaseChangeClassificationTransaction extends BaseChangeEmployeeTransaction {

    public BaseChangeClassificationTransaction(int empId) {
        super(empId);
    }

    protected abstract BasePaymentClassification getClassification();
    protected abstract PaymentSchedule getSchedule();

    @Override
    public void change(Employee e) {
        e.setPaymentClassification(getClassification());
        e.setPaymentSchedule(getSchedule());
    }
}
