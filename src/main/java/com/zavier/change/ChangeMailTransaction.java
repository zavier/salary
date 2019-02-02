package com.zavier.change;

import com.zavier.paymethod.MailMethod;
import com.zavier.paymethod.PaymentMethod;

/**
 * 修改支付方式为邮寄支票
 */
public class ChangeMailTransaction extends BaseChangeMethodTransaction {

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
