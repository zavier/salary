package com.zavier;

import com.zavier.classification.HourlyClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;

import java.math.BigDecimal;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {

    private BigDecimal hourlyRate;

    public ChangeHourlyTransaction(int empId, BigDecimal hourlyRate) {
        super(empId);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
