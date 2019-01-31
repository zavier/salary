package com.zavier;

import com.zavier.classification.HourlyClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.classification.TimeCard;

import java.time.LocalDate;

public class TimeCardTransaction implements Transaction {

    private LocalDate itsDate;
    private double itsHours;
    private int itsEmpid;

    public TimeCardTransaction(LocalDate itsData, double itsHours, int itsEmpid) {
        this.itsDate = itsData;
        this.itsHours = itsHours;
        this.itsEmpid = itsEmpid;
    }

    @Override
    public void execute() {
        Employee e = GpayrollDatabase.getEmployee(itsEmpid);
        if (e != null) {
            PaymentClassification pc = e.getPaymentClassfication();
            if (pc instanceof HourlyClassification) {
                HourlyClassification hc = (HourlyClassification) pc;
                hc.addTimeCard(new TimeCard(itsDate, itsHours));
            } else {
                throw new RuntimeException("Tried to add timecard to non-hourly employee");
            }
        } else {
            throw new RuntimeException("No Such Employee: " + itsEmpid);
        }
    }
}
