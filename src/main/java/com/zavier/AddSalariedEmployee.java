package com.zavier;


import com.zavier.classification.PaymentClassification;
import com.zavier.classification.SalariedClassification;
import com.zavier.payschedule.MonthlySchedule;
import com.zavier.payschedule.PaymentSchedule;

public class AddSalariedEmployee extends AddEmployeeTransaction {

    private double itsSalary;

    public AddSalariedEmployee(int empid, String name, String address, double salary) {
        super(empid, name, address);
        this.itsSalary = salary;
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }

    @Override
    public PaymentClassification getClassfication() {
        return new SalariedClassification(itsSalary);
    }
}
