package com.zavier;

public class ChangeHoldTransaction extends ChangeMethodTransaction{

    public ChangeHoldTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new HoldMethod();
    }
}
