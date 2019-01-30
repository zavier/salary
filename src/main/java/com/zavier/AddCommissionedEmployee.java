package com.zavier;

import com.zavier.classification.CommissionedClassification;
import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.BiWeeklySchedule;
import com.zavier.payschedule.PaymentSchedule;

public class AddCommissionedEmployee extends AddEmployeeTransaction {

    private double salary;
    private double commissionRate;

    public AddCommissionedEmployee(int itsEmpid, String itsName, String itsAddress, double salary,
        double commissionRate) {
        super(itsEmpid, itsName, itsAddress);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiWeeklySchedule();
    }

    @Override
    public PaymentClassification getClassfication() {
        return new CommissionedClassification(salary, commissionRate);
    }
}
