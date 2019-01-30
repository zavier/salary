package com.zavier;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UnionAffiliation implements Affiliation {
    private int memberId;
    private double dues;
    private List<ServiceCharge> serviceChargeList;

    public UnionAffiliation(int memberId, double dues) {
        this.memberId = memberId;
        this.dues = dues;
        serviceChargeList = new ArrayList<>();
    }

    public void addServiceCharge(LocalDate date, double dues) {
        ServiceCharge serviceCharge = new ServiceCharge(date, dues);
        serviceChargeList.add(serviceCharge);
    }

    public int getMemberId() {
        return memberId;
    }

    public double getDues() {
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
    public double calculateDeductions(Paycheck pc) {
        double totalDues = 0;
        LocalDate startDate = pc.getPayPeriodStartDate();
        LocalDate endDate = pc.getPayPeriodEndDate();
        int fridays = numberOfFirdaysInPayPeriod(startDate, endDate);
        for (ServiceCharge serviceCharge : serviceChargeList) {
            LocalDate date = serviceCharge.getDate();
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                totalDues += serviceCharge.getAmount();
            }
        }
        totalDues += dues * fridays;
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
