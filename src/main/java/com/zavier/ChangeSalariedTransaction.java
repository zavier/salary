package com.zavier;

import com.zavier.classification.PaymentClassification;
import com.zavier.classification.SalariedClassification;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {

    private double salary;

    public ChangeSalariedTransaction(int empId, double salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getClassificiation() {
        return new SalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
