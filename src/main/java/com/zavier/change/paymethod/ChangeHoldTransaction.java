package com.zavier.change.paymethod;

import com.zavier.paymethod.HoldMethod;
import com.zavier.paymethod.PaymentMethod;

/**
 * 修改支付方式为由出纳保管
 */
public class ChangeHoldTransaction extends BaseChangeMethodTransaction {

    public ChangeHoldTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new HoldMethod();
    }
}
