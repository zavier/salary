package com.zavier;

import com.zavier.paymethod.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

    public ChangeMethodTransaction(int empId) {
        super(empId);
    }

    protected abstract PaymentMethod getMethod();

    @Override
    public void change(Employee e) {
        e.setPaymentMethod(getMethod());
    }
}
