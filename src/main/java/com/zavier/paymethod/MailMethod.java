package com.zavier.paymethod;

import com.zavier.Paycheck;
import com.zavier.paymethod.PaymentMethod;

/**
 * 邮寄支票发放薪水
 */
public class MailMethod implements PaymentMethod {
    /**
     * 邮寄地址
     */
    private String address;

    public MailMethod(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void pay(Paycheck pc) {

    }
}
