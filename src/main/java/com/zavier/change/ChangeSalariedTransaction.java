package com.zavier.change;

import com.zavier.classification.PaymentClassification;
import com.zavier.classification.SalariedClassification;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;

import java.math.BigDecimal;

public class ChangeSalariedTransaction extends BaseChangeClassificationTransaction {

    private BigDecimal salary;

    public ChangeSalariedTransaction(int empId, BigDecimal salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
