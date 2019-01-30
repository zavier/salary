package com.zavier.classification;

import com.zavier.Paycheck;
import com.zavier.SalesReceipt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommissionedClassification extends PaymentClassification {

    private double salary;
    private double commissionRate;
    private List<SalesReceipt> salesReceiptList;

    public CommissionedClassification(double salary, double commissionRate) {
        this.salary = salary;
        this.commissionRate = commissionRate;
        salesReceiptList = new ArrayList<>();
    }

    public double getCommissionRate() {
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
    public double calculatePay(Paycheck pc) {
        LocalDate payPeriod = pc.getPayDate();
        int totalMoney = 0;
        for (SalesReceipt salesReceipt : salesReceiptList) {
            if (isInPayPeriod(salesReceipt.getDate(), pc)) {
                totalMoney += salesReceipt.getAmount();
            }
        }
        return totalMoney * commissionRate + salary;
    }
}
