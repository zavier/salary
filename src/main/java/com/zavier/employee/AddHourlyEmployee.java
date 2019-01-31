package com.zavier.employee;

import com.zavier.classification.HourlyClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;

import java.math.BigDecimal;

/**
 * 添加小时工雇员
 */
public class AddHourlyEmployee extends AbstractAddEmployeeTransaction {

    private BigDecimal hourlyRate;

    public AddHourlyEmployee(int empId, String name, String address, BigDecimal hourlyRate) {
        super(empId, name, address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }

    @Override
    public PaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }
}
