package com.zavier.change;

import com.zavier.employee.Employee;
import com.zavier.paymethod.PaymentMethod;

public abstract class ChangeMethodTransaction extends BaseChangeEmployeeTransaction {

    public ChangeMethodTransaction(int empId) {
        super(empId);
    }

    protected abstract PaymentMethod getMethod();

    @Override
    public void change(Employee e) {
        e.setPaymentMethod(getMethod());
    }
}
