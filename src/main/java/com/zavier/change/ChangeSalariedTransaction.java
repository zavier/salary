package com.zavier.change;

import com.zavier.classification.BasePaymentClassification;
import com.zavier.classification.SalariedClassification;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;

/**
 * 修改计薪方式为月薪结算
 */
public class ChangeSalariedTransaction extends BaseChangeClassificationTransaction {

    private BigDecimal salary;

    public ChangeSalariedTransaction(int empId, BigDecimal salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    protected BasePaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
