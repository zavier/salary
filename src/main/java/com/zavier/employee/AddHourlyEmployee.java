package com.zavier.employee;

import com.zavier.classification.BasePaymentClassification;
import com.zavier.classification.HourlyClassification;
import com.zavier.payschedule.PaymentSchedule;
import com.zavier.payschedule.WeeklySchedule;

import java.math.BigDecimal;

/**
 * 添加小时工雇员
 */
public class AddHourlyEmployee extends BaseAddEmployeeTransaction {

    /**
     * 每小时薪水
     */
    private BigDecimal hourlyRate;

    public AddHourlyEmployee(int empId, String name, String address, BigDecimal hourlyRate) {
        super(empId, name, address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return new WeeklySchedule();
    }

    @Override
    protected BasePaymentClassification makeClassification() {
        return new HourlyClassification(hourlyRate);
    }
}
