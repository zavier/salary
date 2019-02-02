package com.zavier.employee;

import com.zavier.classification.CommissionedClassification;
import com.zavier.classification.BasePaymentClassification;
import com.zavier.payschedule.BiWeeklySchedule;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;

/**
 * 添加销售雇员
 */
public class AddCommissionedEmployee extends BaseAddEmployeeTransaction {

    /**
     * 底薪
     */
    private BigDecimal salary;

    /**
     * 销售提成比例
     */
    private BigDecimal commissionRate;

    public AddCommissionedEmployee(int empId, String name, String address, BigDecimal salary,
                                   BigDecimal commissionRate) {
        super(empId, name, address);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiWeeklySchedule();
    }

    @Override
    public BasePaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }
}
