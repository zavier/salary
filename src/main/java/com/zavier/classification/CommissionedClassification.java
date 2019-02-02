package com.zavier.classification;

import com.zavier.pay.Paycheck;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 底薪加销售提成结薪
 */
public class CommissionedClassification extends BasePaymentClassification {
    /**
     * 底薪
     */
    private BigDecimal salary;

    /**
     * 销售提成比例
     */
    private BigDecimal commissionRate;

    /**
     * 销售凭条
     */
    private List<SalesReceipt> salesReceiptList;

    public CommissionedClassification(BigDecimal salary, BigDecimal commissionRate) {
        this.salary = salary;
        this.commissionRate = commissionRate;
        salesReceiptList = new ArrayList<>();
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceiptList.add(salesReceipt);
    }

    public SalesReceipt getSalesReceipt(LocalDate date) {
        for (SalesReceipt salesReceipt : salesReceiptList) {
            if (salesReceipt.getDate().equals(date)) {
                return salesReceipt;
            }
        }
        return null;
    }

    @Override
    public BigDecimal calculatePay(Paycheck pc) {
        LocalDate payPeriod = pc.getPayDate();
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (SalesReceipt salesReceipt : salesReceiptList) {
            if (isInPayPeriod(salesReceipt.getDate(), pc)) {
                totalMoney = totalMoney.add(salesReceipt.getAmount());
            }
        }
        return totalMoney.multiply(commissionRate).add(salary);
    }
}
