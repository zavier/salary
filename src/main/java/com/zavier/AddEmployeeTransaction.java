package com.zavier;


import com.zavier.classification.PaymentClassification;
import com.zavier.payschedule.PaymentSchedule;

public abstract class AddEmployeeTransaction implements Transaction {

    private int itsEmpid;
    private String itsName;
    private String itsAddress;

    public AddEmployeeTransaction(int itsEmpid, String itsName, String itsAddress) {
        this.itsEmpid = itsEmpid;
        this.itsName = itsName;
        this.itsAddress = itsAddress;
    }

    @Override
    public void execute() {
        PaymentClassification pc = getClassfication();
        PaymentSchedule ps = getSchedule();
        PaymentMethod pm = new HoldMethod();
        Employee e = new Employee(itsEmpid, itsName, itsAddress);
        e.setPaymentClassfication(pc);
        e.setPaymentSchedule(ps);
        e.setPaymentMethod(pm);
        e.setAffilication(new NoAffiliation());
        GpayrollDatabase.addEmployee(itsEmpid, e);
    }

    public abstract PaymentSchedule getSchedule();

    public abstract PaymentClassification getClassfication();
}
