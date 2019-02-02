package com.zavier.change;

import com.zavier.employee.Employee;
import com.zavier.paymethod.PaymentMethod;

/**
 * 修改支付方式
 */
public abstract class BaseChangeMethodTransaction extends BaseChangeEmployeeTransaction {

    public BaseChangeMethodTransaction(int empId) {
        super(empId);
    }

    protected abstract PaymentMethod getMethod();

    @Override
    public void change(Employee e) {
        e.setPaymentMethod(getMethod());
    }
}
