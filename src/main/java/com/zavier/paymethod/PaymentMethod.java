package com.zavier.paymethod;

import com.zavier.pay.Paycheck;

/**
 * 支付方式
 */
public interface PaymentMethod {
    /**
     * 进行支付
     * @param pc
     */
    void pay(Paycheck pc);
}
