package com.zavier;

import com.zavier.classification.HourlyClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {

    private double hourlyRate;

    public ChangeHourlyTransaction(int empId, double hourlyRate) {
        super(empId);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getClassificiation() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
