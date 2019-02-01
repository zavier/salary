package com.zavier.change;

import com.zavier.paymethod.HoldMethod;
import com.zavier.paymethod.PaymentMethod;

public class ChangeHoldTransaction extends ChangeMethodTransaction {

    public ChangeHoldTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new HoldMethod();
    }
}
