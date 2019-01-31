package com.zavier.paymethod;

import com.zavier.Paycheck;
import com.zavier.paymethod.PaymentMethod;

/**
 * 薪水发到指定银行账号
 */
public class DirectMethod implements PaymentMethod {
    /**
     * 银行
     */
    private String bank;

    /**
     * 账号
     */
    private String account;

    public DirectMethod(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public void pay(Paycheck pc) {

    }
}
