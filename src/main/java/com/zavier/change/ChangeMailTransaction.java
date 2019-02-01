package com.zavier.change;

import com.zavier.paymethod.MailMethod;
import com.zavier.paymethod.PaymentMethod;

public class ChangeMailTransaction extends ChangeMethodTransaction {

    private String address;

    public ChangeMailTransaction(int empId, String address) {
        super(empId);
        this.address = address;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new MailMethod(address);
    }
}
