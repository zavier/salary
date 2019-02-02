package com.zavier.change;

import com.zavier.paymethod.DirectMethod;
import com.zavier.paymethod.PaymentMethod;

/**
 * 修改支付方式为付款到指定银行卡
 */
public class ChangeDirectTransaction extends BaseChangeMethodTransaction {
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
