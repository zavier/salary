package com.zavier;

import com.zavier.classification.HourlyClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;

public class AddHourlyEmployee extends AddEmployeeTransaction {

    private double hourlyRate;

    public AddHourlyEmployee(int itsEmpid, String itsName, String itsAddress, double hourlyRate) {
        super(itsEmpid, itsName, itsAddress);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }

    @Override
    public PaymentClassification getClassfication() {
        return new HourlyClassification(hourlyRate);
    }
}
