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
    protected PaymentSchedule makeSchedule() {
        return new MonthlySchedule();
    }

    @Override
    protected BasePaymentClassification makeClassification() {
        return new SalariedClassification(itsSalary);
    }
}
