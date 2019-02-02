package com.zavier.employee;


import com.zavier.classification.BasePaymentClassification;
import com.zavier.classification.SalariedClassification;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;

/**
 * 增加月薪雇员
 */
public class AddSalariedEmployee extends BaseAddEmployeeTransaction {

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
    public BasePaymentClassification getClassification() {
        return new SalariedClassification(itsSalary);
    }
}
