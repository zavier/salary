package com.zavier.change;

import com.zavier.classification.HourlyClassification;
import com.zavier.classification.BasePaymentClassification;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;

import java.math.BigDecimal;

/**
 * 修改计薪方式为小时工
 */
public class ChangeHourlyTransaction extends BaseChangeClassificationTransaction {

    private BigDecimal hourlyRate;

    public ChangeHourlyTransaction(int empId, BigDecimal hourlyRate) {
        super(empId);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected BasePaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
