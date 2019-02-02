package com.zavier.paymethod;

import com.zavier.pay.Paycheck;

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
