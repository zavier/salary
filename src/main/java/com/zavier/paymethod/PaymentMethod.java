package com.zavier.paymethod;

import com.zavier.Paycheck;

/**
 * 支付方式
 */
public interface PaymentMethod {
    void pay(Paycheck pc);
}
