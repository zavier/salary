package com.zavier;

import com.zavier.classification.HourlyClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.classification.TimeCard;
import com.zavier.employee.Employee;

import java.time.LocalDate;

/**
 * 小时工工作时间登记
 */
public class TimeCardTransaction implements Transaction {

    /**
     * 工作日期
     */
    private LocalDate date;

    /**
     * 工作时长
     */
    private double hours;

    /**
     * 员工ID
     */
    private int empId;

    public TimeCardTransaction(LocalDate itsData, double hours, int empId) {
        this.date = itsData;
        this.hours = hours;
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = GpayrollDatabase.getEmployee(empId);
        if (e != null) {
            PaymentClassification pc = e.getPaymentClassfication();
            if (pc instanceof HourlyClassification) {
                HourlyClassification hc = (HourlyClassification) pc;
                hc.addTimeCard(new TimeCard(date, hours));
            } else {
                throw new RuntimeException("Tried to add timecard to non-hourly employee");
            }
        } else {
            throw new RuntimeException("No Such Employee: " + empId);
        }
    }
}
