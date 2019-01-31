package com.zavier.employee;


import com.zavier.classification.PaymentClassification;
import com.zavier.classification.SalariedClassification;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;

/**
 * 增加月薪雇员
 */
public class AddSalariedEmployee extends AbstractAddEmployeeTransaction {

    /**
     * 月薪
     */
    private BigDecimal itsSalary;

    public AddSalariedEmployee(int empId, String name, String address, BigDecimal salary) {
        super(empId, name, address);
        this.itsSalary = salary;
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }

    @Override
    public PaymentClassification getClassification() {
        return new SalariedClassification(itsSalary);
    }
}
