package com.zavier.change;

import com.zavier.paymethod.DirectMethod;
import com.zavier.paymethod.PaymentMethod;

public class ChangeDirectTransaction extends ChangeMethodTransaction {
    private String bank;
    private String account;

    public ChangeDirectTransaction(int empId, String bank, String account) {
        super(empId);
        this.bank = bank;
        this.account = account;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new DirectMethod(bank, account);
    }
}
