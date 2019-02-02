package com.zavier.affiliation;

import com.zavier.pay.Paycheck;
import com.zavier.ServiceCharge;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 工会
 */
public class UnionAffiliation implements Affiliation {
    private int memberId;
    private BigDecimal dues;
    private List<ServiceCharge> serviceChargeList;

    public UnionAffiliation(int memberId, BigDecimal dues) {
        this.memberId = memberId;
        this.dues = dues;
        serviceChargeList = new ArrayList<>();
    }

    public void addServiceCharge(LocalDate date, BigDecimal dues) {
        ServiceCharge serviceCharge = new ServiceCharge(date, dues);
        serviceChargeList.add(serviceCharge);
    }

    public int getMemberId() {
        return memberId;
    }

    public BigDecimal getDues() {
        return dues;
    }

    public ServiceCharge getServiceCharge(LocalDate date) {
        for (ServiceCharge charge : serviceChargeList) {
            if (charge.getDate().equals(date)) {
                return charge;
            }
        }
        return null;
    }

    @Override
    public BigDecimal calculateDeductions(Paycheck pc) {
        BigDecimal totalDues = BigDecimal.ZERO;
        LocalDate startDate = pc.getPayPeriodStartDate();
        LocalDate endDate = pc.getPayPeriodEndDate();
        int fridays = numberOfFirdaysInPayPeriod(startDate, endDate);
        for (ServiceCharge serviceCharge : serviceChargeList) {
            LocalDate date = serviceCharge.getDate();
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                totalDues = totalDues.add(serviceCharge.getAmount());
            }
        }
        totalDues = totalDues.add(dues.multiply(BigDecimal.valueOf(fridays)));
        return totalDues;
    }

    public int numberOfFirdaysInPayPeriod(LocalDate payPeriodStart, LocalDate payPeriodEnd) {
        int fridays = 0;
        LocalDate day = payPeriodStart;
        while (day.compareTo(payPeriodEnd) <= 0) {
            if (day.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays++;
            }
            day = day.plusDays(1);
        }
        return fridays;
    }
}
